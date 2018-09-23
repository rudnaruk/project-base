package com.piya.winny.myapplication.base.network;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.piya.winny.myapplication.BuildConfig;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by piyaponf on 9/22/2017 AD.
 */

public abstract class BaseApiService<T> {
    protected String baseUrl;
    private T api;
    private boolean logger;
    private long TIMEOUT = 6000;
    private DefaultHeader defaultHeader;

    protected abstract Class<T> getApiClassType();

    public String getBaseUrl(){
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private boolean isLogger() {
        logger = BuildConfig.DEBUG;
        return logger;
    }

    protected abstract Request.Builder getRequestInterceptor(Request.Builder requestBuilder);

    /**
     * get additional header by services
     * @return Interceptor
     */
    private Interceptor getOnTopInterceptor() {
        //per client interceptor
        //requestBuilder.addHeader("key","value")
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder newRequestBuilder = getRequestInterceptor(chain.request().newBuilder());
                if (newRequestBuilder == null) {
                    return chain.proceed(chain.request());
                }
                return chain.proceed(newRequestBuilder.build());
            }
        };
    }

    /**
     * get default http client
     * @return OkHttpClient
     */
    private OkHttpClient getClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        configCertificate(builder);
        return builder
                .addInterceptor(getDefaultHeader())
                .addInterceptor(getOnTopInterceptor())
                .addNetworkInterceptor(getDefaultHttpLogging(isLogger()))
//                .certificatePinner(getDefaultCertificatePinner())
//                .cookieJar(getDefaultCookieJar())
                .readTimeout(getDefaultTimeout(), TimeUnit.MILLISECONDS)
                .writeTimeout(getDefaultTimeout(), TimeUnit.MILLISECONDS)
                .connectTimeout(getDefaultTimeout(), TimeUnit.MILLISECONDS)
                .build();
    }

    /**
     * get default service Timeout
     * @return timeout millisecond
     */
    protected long getDefaultTimeout() {
        return TIMEOUT;
    }

    /**
     * get default Interceptor
     * @return {@link DefaultHeader} default header
     */
    public abstract DefaultHeader getDefaultHeader();

    /**
     * for bypass self-signed-cert SSL
     * @param builder
     */
    private void configCertificate(OkHttpClient.Builder builder) {
        if (BuildConfig.DEBUG) {
            // Create a trust manager that does not validate certificate chains
            try {
                final TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[]{};
                            }
                        }
                };

                // Install the all-trusting trust manager
                SSLContext sslContext = null;
                try {
                    sslContext = SSLContext.getInstance("SSL");
                    sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (KeyManagementException e) {
                    e.printStackTrace();
                }
                // Create an ssl socket factory with our all-trusting manager
                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

                builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
                builder.hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
            } catch (final Exception e) {
            }
        }
    }

    /**
     * get ApiService object
     * @return Api service calss
     */
    public T getApi(){
        return getBaseRetrofitBuilder().build().create(getApiClassType());
    }

    /**
     * set ApiService object
     * @param api ApiService object
     */
    public void setApi(T api) {
        this.api = api;
    }

    /**
     * for create Retrofit Builder
     * @return Retrofit Builder
     */
    private Retrofit.Builder getBaseRetrofitBuilder() {
        if (addConverter() == null) {
            return new Retrofit.Builder()
                    .baseUrl(getBaseUrl())
                    .client(getClient());
        }
        return new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(addConverter())
                .client(getClient());
    }

    /**
     * for get Http log interceptor
     * @param showLog flag for logging
     * @return HttpLoggingInterceptor
     */
    protected HttpLoggingInterceptor getDefaultHttpLogging(boolean showLog) {
        if (showLog) {
            return new HttpLoggingInterceptor(new BaseHttpLogger()).setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE);
        }
    }

    /**
     * Base Http Logger Class
     */
    class BaseHttpLogger implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {

            final String logName = "OkHttp";
            if (!message.startsWith("{") && !message.startsWith("[")) {
                largeLog(logName, message);
                return;
            }

            try {
                String prettyPrintJson = new GsonBuilder().setPrettyPrinting().create().toJson(new JsonParser().parse(message));
                largeLog(logName, prettyPrintJson);
            } catch (JsonSyntaxException m) {
                Log.d("TRUST", "html header parse failed");
                m.printStackTrace();
                Log.d(logName, message);
            }
        }
    }
    /**
     * return "null" for not use Converter in retrofit.
     */
    protected Converter.Factory addConverter() {
        return GsonConverterFactory.create(new GsonBuilder().setPrettyPrinting().create());
    }

    /**
     * show large log to console
     * @param tag String Tag name
     * @param content message for logging
     */
    public void largeLog(String tag, String content) {
        int MAX_LOG_LENGTH = 4076;
        int offset = 0;
        while (offset + MAX_LOG_LENGTH <= content.length()) {
            Log.d(tag, content.substring(offset, offset += MAX_LOG_LENGTH).replace("&quot;", "\""));
        }

        if (offset < content.length()) {
            Log.d(tag, content.substring(offset).replace("&quot;", "\""));
        }
    }
}

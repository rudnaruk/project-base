package com.piya.winny.myapplication.base.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by piyaponf on 9/25/2017 AD.
 */

public abstract class DefaultHeader implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(getGenericRequest(chain.request().newBuilder()).build());
    }

    abstract public Request.Builder getGenericRequest(Request.Builder builder);
}

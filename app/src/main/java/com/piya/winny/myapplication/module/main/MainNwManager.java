package com.piya.winny.myapplication.module.main;

import com.hwangjr.rxbus.RxBus;
import com.studio.piyaponf.base_project.network.BaseApiService;
import com.studio.piyaponf.base_project.network.DefaultHeader;
import com.studio.piyaponf.base_project.network.ErrorEvent;
import com.piya.winny.myapplication.module.main.model.User;
import com.piya.winny.myapplication.module.main.service.MainApiService;
import com.piya.winny.myapplication.nwmanager.MyDefaultHeader;

import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by piyaponf on 9/22/2017 AD.
 */

public class MainNwManager extends BaseApiService<MainApiService> {

    @Override
    public boolean isLogger() {
        return true;
    }

    public static String SERVICE_GET_USER = "get_user";

    public void getUser(String username){
        setBaseUrl("https://api.github.com");
        MainApiService mMainApiService = getApi();
        Call call = mMainApiService.getUser(username);
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();

                if (user == null) {
                    //404 or the response cannot be converted to User.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        RxBus.get().post(new ErrorEvent(SERVICE_GET_USER));
                    } else {
                        RxBus.get().post(new ErrorEvent(SERVICE_GET_USER));
                    }
                } else {
                    RxBus.get().post(user);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                RxBus.get().post(new ErrorEvent(SERVICE_GET_USER));
            }

        });

    }

    @Override
    protected Class getApiClassType() {
        return MainApiService.class;
    }

    @Override
    protected Request.Builder getRequestInterceptor(Request.Builder requestBuilder) {
        return null;
    }

    @Override
    public DefaultHeader getDefaultHeader() {
        return new MyDefaultHeader();
    }
}

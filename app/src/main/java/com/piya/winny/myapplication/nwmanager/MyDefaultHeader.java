package com.piya.winny.myapplication.nwmanager;

import com.studio.piyaponf.base_project.network.DefaultHeader;

import okhttp3.Request;

/**
 * Created by piyaponf on 9/25/2017 AD.
 */

public class MyDefaultHeader extends DefaultHeader {

    @Override
    public Request.Builder getGenericRequest(Request.Builder builder) {
        return builder.addHeader("X-Client-Channel", "CCC");
    }

}

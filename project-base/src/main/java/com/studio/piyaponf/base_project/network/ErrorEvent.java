package com.studio.piyaponf.base_project.network;

import java.util.Arrays;
import java.util.List;

/**
 * Created by piyaponf on 9/25/2017 AD.
 */

public class ErrorEvent {
    private String serviceName;

    public ErrorEvent(String serviceName) {
        this.serviceName = serviceName;
    }

    public boolean isMatch(String... serviceNameArray) {
        List<String> serviceNameList = Arrays.asList(serviceNameArray);
        return serviceNameList.contains(serviceName);
    }

    public String getServiceName() {
        return serviceName;
    }
}

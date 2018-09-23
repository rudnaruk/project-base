package com.piya.winny.myapplication.base;

/**
 * Created by piyaponf on 9/21/2017 AD.
 */

public interface BaseActivityContractorView<T extends BaseActivityContractorController> {
    void setController(T controller);
}

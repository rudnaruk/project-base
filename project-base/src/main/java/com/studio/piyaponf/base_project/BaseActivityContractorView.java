package com.studio.piyaponf.base_project;

/**
 * Created by piyaponf on 9/21/2017 AD.
 */

public interface BaseActivityContractorView<T extends BaseActivityContractorController> {
    void setController(T controller);
}

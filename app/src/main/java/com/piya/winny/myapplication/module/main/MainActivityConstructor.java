package com.piya.winny.myapplication.module.main;

import com.studio.piyaponf.base_project.BaseActivityContractorController;
import com.studio.piyaponf.base_project.BaseActivityContractorView;

/**
 * Created by piyaponf on 9/21/2017 AD.
 */

public class MainActivityConstructor {

    public interface Controller extends BaseActivityContractorController {
        void loadData();
    }
    public interface View extends BaseActivityContractorView<Controller> {
        void showToastHello();
    }
}

package com.piya.winny.myapplication.module.main;

import com.piya.winny.myapplication.base.BaseActivityContractorController;
import com.piya.winny.myapplication.base.BaseActivityContractorView;

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

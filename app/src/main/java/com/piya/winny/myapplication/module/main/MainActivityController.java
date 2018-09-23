package com.piya.winny.myapplication.module.main;

import android.util.Log;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.piya.winny.myapplication.module.main.model.User;
import com.studio.piyaponf.base_project.BaseActivityContractorView;
import com.studio.piyaponf.base_project.BaseActivityController;
import com.studio.piyaponf.base_project.network.ErrorEvent;

/**
 * Created by piyaponf on 9/21/2017 AD.
 */

public class MainActivityController extends BaseActivityController implements MainActivityConstructor.Controller{

    protected MainActivityController(BaseActivityContractorView view) {
        super(view);
    }


    public static MainActivityController createController(MainActivityConstructor.View view){
        return new MainActivityController(view);
    }

    @Override
    public void onViewCreate() {
        MainNwManager mMainNwManager = new MainNwManager();
        mMainNwManager.getUser("rudnaruk");
    }

    @Override
    public void onViewDestroy() {

    }

    @Override
    public void onViewStart() {
        RxBus.get().register(this);
    }

    @Override
    public void onViewStop() {
        RxBus.get().unregister(this);
    }

    @Override
    public void loadData() {
        ((MainActivity)getView()).showToastHello();
        Log.d("MainActivityController","loadData.....");
    }

    @Subscribe
    public void onResponse(User user) {
        String data = "Github Name :" + user.getName() +
                "\nBlog :"+user.getBlog() +
                "\nCompany Name :" + user.getCompany();
        ((MainActivity)getView()).showToastText(data);
    }

    @Subscribe
    public void onErrorEvent(ErrorEvent error) {
        ((MainActivity)getView()).showToastText("Error!");
    }

}

package com.piya.winny.myapplication.module.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.piya.winny.myapplication.R;
import com.studio.piyaponf.base_project.BaseActivity;

public class MainActivity extends BaseActivity<MainActivityConstructor.Controller> implements MainActivityConstructor.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected MainActivityConstructor.Controller createController() {
        return MainActivityController.createController(this);
    }

    @Override
    public void bindView(View view) {

    }

    @Override
    public void setupView() {

    }

    @Override
    public void initialize() {
        getController().loadData();
    }

    @Override
    public void showToastHello() {
        Toast.makeText(getApplicationContext(),"Hello!",Toast.LENGTH_SHORT).show();
    }

    public void showToastText(String text){
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
    }
}

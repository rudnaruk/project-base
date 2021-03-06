package com.studio.piyaponf.base_project;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by piyaponf on 9/21/2017 AD.
 * MCV Activity class
 */
public abstract class BaseActivity<T extends BaseActivityContractorController> extends AppCompatActivity
        implements BaseActivityContractorView<T> {

    // a Controller
    private T controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = createController();
        bindView(getWindow().getDecorView().getRootView());
        setupView();
        initialize();
        getController().onViewCreate();
    }

    /**
     * For create and assign controller to Activity
     *
     * @return T generics Controller class
     */
    protected abstract T createController();

    /**
     * for binding view to activity attributes
     *
     * @param view
     */
    public abstract void bindView(View view);

    /**
     * for set up view invoked after {@link #bindView(View)} method
     */
    public abstract void setupView();


    /**
     * For initial value at start point
     */
    public abstract void initialize();

    /**
     * for get Controller
     *
     * @return T generics Controller class
     */
    public T getController() {
        return controller;
    }

    @Override
    public void setController(T controller) {
        this.controller = controller;
    }

    @Override
    protected void onDestroy() {
        getController().onViewDestroy();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        getController().onViewStart();
        super.onStart();
    }

    @Override
    protected void onStop() {
        getController().onViewStop();
        super.onStop();
    }
}

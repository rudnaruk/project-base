package com.studio.piyaponf.base_project;

import java.lang.ref.WeakReference;

/**
 * Created by piyaponf on 9/22/2017 AD.
 */

public abstract class BaseActivityController<CV extends BaseActivityContractorView> implements BaseActivityContractorController {

    private WeakReference<CV> view;

    @SuppressWarnings("unchecked")
    protected BaseActivityController(CV view) {
        //--set view to controller---
        this.view = new WeakReference<>(view);
        view.setController(this);
    }

    public CV getView() {
        return view.get();
    }

}

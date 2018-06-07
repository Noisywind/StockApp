/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package com.jinkan.www.stock.base;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.databinding.library.baseAdapters.BR;


/**
 * Created by lushengbo on 2018/1/12.
 * MVVM View基类
 */

public abstract class BaseMVVMActivity<VM extends BaseViewModel, VDB extends ViewDataBinding> extends BaseActivity {
    protected VM mViewModel;
    protected VDB mViewDataBinding;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mViewModel = createdViewModel();
        super.onCreate(savedInstanceState);
        mViewModel.attachView(this);
        mViewModel.init(transferData);
    }

    protected abstract VM createdViewModel();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.clear();
        mViewModel.detachView();
    }

    @Override
    protected void setMyContentView() {
        mViewDataBinding = DataBindingUtil.setContentView(this, setLayoutRes());
        mViewDataBinding.setVariable(BR.model, mViewModel);

    }


    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode 请求码
     * @param resultCode  应答码
     * @param data        数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mViewModel.onActivityResult(requestCode, resultCode, data);
    }
}

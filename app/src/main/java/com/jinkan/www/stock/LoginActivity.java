package com.jinkan.www.stock;

import com.jinkan.www.stock.base.BaseActivity;

/**
 * Created by Sampson on 2018/6/7.
 * StockApp
 */
public class LoginActivity extends BaseActivity {
    @Override
    protected String setToolBarTitle() {
        return "登录";
    }

    @Override
    protected int setToolBarMenu() {
        return 0;
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

    }
}

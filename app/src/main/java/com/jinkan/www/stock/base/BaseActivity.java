package com.jinkan.www.stock.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.jinkan.www.stock.R;


/**
 * Created by Sampson on 2018/4/16.
 * FastAndroid
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Object transferData;
    protected FragmentManager mFragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMyContentView();
        mFragmentManager = getSupportFragmentManager();
        getTransferData();
        setToolBar();
    }

    protected void setMyContentView() {
        setContentView(setLayoutRes());
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int toolBarMenu = setToolBarMenu();
        if (toolBarMenu != 0) {
            getMenuInflater().inflate(toolBarMenu, menu);//加载menu文件到布局
        }
        return super.onCreateOptionsMenu(menu);

    }

    private void getTransferData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLE");
        if (bundle != null) {
            transferData = bundle.get("DATA");
        } else {
            transferData = null;
        }
    }

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.tool_bar);

        if (toolbar != null) {
            if (setToolBarTitle() != null)
                toolbar.setTitle(setToolBarTitle());
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
    }


    protected abstract String setToolBarTitle();

    public void setFragment(@IdRes int containerViewId, Fragment fragment) {
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(containerViewId, fragment);
        mFragmentTransaction.commit();
    }

    public void setFragment(@IdRes int containerViewId, Fragment fragment, @Nullable Object data) {
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        if (data instanceof String) {
            bundle.putString("DATA", String.valueOf(data));
        } else if (data instanceof Integer) {
            bundle.putInt("DATA", (Integer) data);
        } else if (data instanceof String[]) {
            bundle.putStringArray("DATA", (String[]) data);
        } else if (data instanceof Parcelable) {
            bundle.putParcelable("DATA", (Parcelable) data);
        }
        fragment.setArguments(bundle);
        mFragmentTransaction.replace(containerViewId, fragment);
        mFragmentTransaction.commit();
    }

    /**
     * 跳转
     *
     * @param data   参数
     * @param mClass 类名
     */
    public void skipTo(Class<?> mClass, Object data) {
        if (mClass.isAssignableFrom(BaseActivity.class)) {
            Intent intent = new Intent();
            skipTo(mClass, data, intent);
        }
    }

    /**
     * 跳转
     *
     * @param data   参数
     * @param mClass 类名
     * @param isTop  是否关闭其它页面
     */
    public void skipTo(Class mClass, Object data, boolean isTop) {
        Intent intent = new Intent();
        intent.setClass(this, mClass);
        if (isTop) {
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        skipTo(mClass, data, intent);
    }

    private void skipTo(Class<?> mClass, Object data, Intent intent) {
        intent.setClass(this, mClass);
        if (data != null) {
            Bundle bundle = new Bundle();
            if (data instanceof String) {
                bundle.putString("DATA", String.valueOf(data));
            } else if (data instanceof Integer) {
                bundle.putInt("DATA", (Integer) data);
            } else if (data instanceof String[]) {
                bundle.putStringArray("DATA", (String[]) data);
            } else if (data instanceof Parcelable) {
                bundle.putParcelable("DATA", (Parcelable) data);
            }
            intent.putExtra("BUNDLE", bundle);
        }
        startActivity(intent);
    }


    protected abstract @MenuRes
    int setToolBarMenu();

    protected abstract @LayoutRes
    int setLayoutRes();

    protected abstract void initView();
}

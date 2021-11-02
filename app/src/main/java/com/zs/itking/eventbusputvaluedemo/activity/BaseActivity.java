package com.zs.itking.eventbusputvaluedemo.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zs.itking.eventbusputvaluedemo.base.eventbus.GlobalBus;

/**
 * created by on 2021/11/2
 * 描述：
 *
 * @author ZSAndroid
 * @create 2021-11-02-14:28
 */
public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** 初始化绑定布局 */
        setContentView(bindLayout());
        /** 初始化注册EventBus */
        initRegisterEventBus(isRegisterEventBus());
        /** 初始化视图 */
        initView();
        /** 初始化数据 */
        initData(savedInstanceState);
    }

    /**
     * 绑定子类布局
     *
     * @return 布局文件的资源ID
     */
    public abstract int bindLayout();

    public abstract boolean isRegisterEventBus();

    /**
     * 注册EventBus
     *
     * @param state true --> 注册
     */
    public void initRegisterEventBus(Boolean state) {
        if (state) {
            GlobalBus.getBus().register(this);
        }
    }

    /**
     * 初始化视图，findViewById等等
     */
    protected abstract void initView();

    /**
     * 初始化数据，从本地或服务器开始获取数据
     *
     * @param savedInstanceState 界面非正常销毁时保存的数据
     */
    protected abstract void initData(Bundle savedInstanceState);


    /**
     * 暂停时：注销EventBus
     */
    @Override
    protected void onStop() {
        super.onStop();
        GlobalBus.getBus().unregister(this);
    }
    /**
     * 销毁时：注销EventBus
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalBus.getBus().unregister(this);
    }
}

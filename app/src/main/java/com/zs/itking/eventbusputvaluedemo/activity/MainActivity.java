package com.zs.itking.eventbusputvaluedemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zs.itking.eventbusputvaluedemo.MyShop;
import com.zs.itking.eventbusputvaluedemo.R;
import com.zs.itking.eventbusputvaluedemo.base.AnyEventType;
import com.zs.itking.eventbusputvaluedemo.fragment.OneFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity {

    private OneFragment oneFragment;
    private AnyEventType anyEventType;

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    public void but(View view) {
        EventBus.getDefault().postSticky(new AnyEventType("我是传输的数据"));
        addFragment(new AnyEventType("我是传输的数据"));
        startActivity(new Intent(MainActivity.this, TwoActivity.class));
    }


    private void addFragment(AnyEventType anyEventType){
        if(isFinishing()){
            return;
        }
        if(oneFragment != null){
            return;
        }
        oneFragment = oneFragment.newInstance();//在实例MyFragment时，便注册EventBus，保证在post前得到register
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_show, oneFragment).commitAllowingStateLoss();
        EventBus.getDefault().post(anyEventType);//向Fragment发送数据, 注意post操作应该放在MyFragment实例之后，即先register
    }

    private AnyEventType initDat1a(){
        anyEventType = new AnyEventType("我是传输的数据");
        return anyEventType;
    }

    //不可省略注解，否则会crash
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String data) {

    }

}
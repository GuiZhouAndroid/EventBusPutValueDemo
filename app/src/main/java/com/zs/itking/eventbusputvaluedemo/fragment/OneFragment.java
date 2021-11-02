package com.zs.itking.eventbusputvaluedemo.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zs.itking.eventbusputvaluedemo.MyShop;
import com.zs.itking.eventbusputvaluedemo.base.AnyEventType;
import com.zs.itking.eventbusputvaluedemo.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * created by on 2021/11/2
 * 描述：
 *
 * @author ZSAndroid
 * @create 2021-11-02-13:23
 */

public class OneFragment extends Fragment {

    private AnyEventType myShop;
    private TextView tv_s;

    public static OneFragment newInstance(){
        OneFragment fragment = new OneFragment();
        EventBus.getDefault().register(fragment);
        Log.d(fragment.getClass().getName(), "EventBus注册");
        return fragment;
    }

    public OneFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(getClass().getName(), "fragment： onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(getClass().getName(), "fragment： onCreateView");
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        tv_s = view.findViewById(R.id.tv_s);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(getClass().getName(), "fragment： onActivityCreate");
        initView();
    }

    private void initView(){
        if(myShop != null){
            tv_s.setText(myShop.getMsg());
        }
    }

    /**
     * 根据生命周器，EventBus接收数据发生在View的实例化和绘制之前，那么如何完成刷新UI操作呢？
     * 错误的做法：在接收数据的同事去刷新UI（因为此时view还没有绘制）
     * 正确的做法：先接收数据，然后在onActivityCreated中，利用接收的数据去刷新UI
     * @param shop
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent (AnyEventType shop) {
        if (shop != null) {
            Log.d(getClass().getName(), "接收到EventBus数据");
            myShop = shop;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(getClass().getName(), "fragment： onDestroy");
        EventBus.getDefault().unregister(this);//取消注册
        Log.d(getClass().getName(), "EventBus取消注册");
    }
}


package com.zs.itking.eventbusputvaluedemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zs.itking.eventbusputvaluedemo.R;
import com.zs.itking.eventbusputvaluedemo.base.eventbus.Events;
import com.zs.itking.eventbusputvaluedemo.base.eventbus.GlobalBus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Random;

/**
 * created by on 2021/11/2
 * 描述：Fragment 与 Fragment 通过EventBus传值
 *
 * @author ZSAndroid
 * @create 2021-11-02-13:23
 */

public class ThreeFragment extends Fragment {

    private static Events.FragmentActivityMessage fragmentActivityMessage;
    private Button submit;

    public static ThreeFragment newInstance(){
        ThreeFragment fragment = new ThreeFragment();
        GlobalBus.getBus().register(fragment);
        return fragment;
    }

    public ThreeFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        submit = view.findViewById(R.id.submit);
        //向MainActivity传值

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }


    private void initView(){
        //点击按钮开始MainActivity传过来的值
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalBus.getBus().post(new Events.FragmentActivityMessage("我是ThreeFragment传到MainActivity的数据"));
            }
        });
    }

    @Subscribe(sticky = true)
    public void onEvent(String data) {

    }

    /**
     * 注销EventBus
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        GlobalBus.getBus().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //Unregister the Registered Event.
        GlobalBus.getBus().unregister(this);
    }
}


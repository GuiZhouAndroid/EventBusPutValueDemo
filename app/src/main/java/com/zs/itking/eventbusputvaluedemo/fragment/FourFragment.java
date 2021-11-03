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

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

/**
 * created by on 2021/11/2
 * 描述：
 *
 * @author ZSAndroid
 * @create 2021-11-02-13:23
 */

public class FourFragment extends Fragment {
    private Events.ActivityFragmentMessage activityFragmentMessage;

    private TextView tv_ss;

    public static FourFragment newInstance(){
        FourFragment fragment = new FourFragment();
        GlobalBus.getBus().register(fragment);
        return fragment;
    }

    public FourFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four, container, false);
        tv_ss = view.findViewById(R.id.tv_ss);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //设置MainActivity传过来的值
        initView();
    }

    private void initView(){
        if(activityFragmentMessage != null){
            tv_ss.setText("FourFragment："+activityFragmentMessage.getMessage());
        }
        tv_ss.setText("FourFragment：");
    }

    /**
     * 根据生命周器，EventBus接收数据发生在View的实例化和绘制之前，那么如何完成刷新UI操作呢？
     * 错误的做法：在接收数据的同事去刷新UI（因为此时view还没有绘制）
     * 正确的做法：先接收数据，然后在onActivityCreated中，利用接收的数据去刷新UI
     * @param Message
     */

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent (Events.ActivityFragmentMessage Message) {
        //对象数据赋值给当前类
        if (Message != null) {
            activityFragmentMessage = Message;
        }
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

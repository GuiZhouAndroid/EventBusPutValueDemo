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

/**
 * created by on 2021/11/2
 * 描述：Fragment 与 Fragment 通过EventBus传值
 *
 * @author ZSAndroid
 * @create 2021-11-02-13:23
 */

public class OneFragment extends Fragment {

    private Events.ActivityFragmentMessage activityFragmentMessage;
    private Events.FragmentFragmentMessage fragmentFragmentMessage;
    private static Events.ActivityActivityMessage activityActivityMessage;
    private TextView tv_s;
    private Button btn_one;
    private TwoFragment twoFragment;

    public static OneFragment newInstance(){
        OneFragment fragment = new OneFragment();
        EventBus.getDefault().register(fragment);
        return fragment;
    }

    public OneFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        tv_s = view.findViewById(R.id.tv_s);
        btn_one = view.findViewById(R.id.btn_one);
        //向TwoFragment传值
        btn_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity().isFinishing()){
                    return;
                }
                if(twoFragment != null){
                    return;
                }
                twoFragment = TwoFragment.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_show,twoFragment).commitAllowingStateLoss();
                GlobalBus.getBus().post(new Events.FragmentFragmentMessage("我是OneFragment传到TwoFragment的数据"));
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }


    private void initView(){
        //设置MainActivity传过来的值
        if(activityFragmentMessage != null){
            tv_s.setText("OneFragment："+activityFragmentMessage.getMessage());
        }
        //设置TwoFragment传过来的值
        if(fragmentFragmentMessage != null){
            tv_s.setText("TwoFragment："+fragmentFragmentMessage.getMessage());
        }
    }


    /**
     * 接收Events.ActivityFragmentMessage类型事件
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
     * 接收Events.FragmentFragmentMessage类型事件
     * @param message TwoFragment传递的数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent (Events.FragmentFragmentMessage message) {
        //对象数据赋值给当前类
        if (message != null) {
            fragmentFragmentMessage = message;
        }
    }

    /**
     * 接收Events.ActivityActivityMessage类型事件
     * @param message  TwoActivity传递的数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Events.ActivityActivityMessage message) {
        //接收TwoActivity传来字符串类型的文本数据，Toast提示
        if (message != null) {
            activityActivityMessage = message;
            Toast.makeText(getActivity(), "OneFragment："+activityActivityMessage.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消注册
    }
}


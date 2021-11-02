package com.zs.itking.eventbusputvaluedemo.fragment;

import android.os.Bundle;
import android.util.Log;
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
 * 描述：
 *
 * @author ZSAndroid
 * @create 2021-11-02-13:23
 */

public class TwoFragment extends Fragment {
    private Events.ActivityFragmentMessage activityFragmentMessage;
    private Events.FragmentFragmentMessage fragmentFragmentMessage;
    private static Events.ActivityActivityMessage activityActivityMessage;

    private TextView tv_s;
    private OneFragment oneFragment;
    private Button btn_two;
    public static TwoFragment newInstance(){
        TwoFragment fragment = new TwoFragment();
        EventBus.getDefault().register(fragment);
        Log.d(fragment.getClass().getName(), "EventBus注册");
        return fragment;
    }

    public TwoFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(getClass().getName(), "fragment： onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(getClass().getName(), "fragment： onCreateView");
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        tv_s = view.findViewById(R.id.tv_s);
        btn_two = view.findViewById(R.id.btn_two);
        //向TwoFragment传值
        btn_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity().isFinishing()){
                    return;
                }
                if(oneFragment != null){
                    return;
                }
                oneFragment = OneFragment.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_show,oneFragment).commitAllowingStateLoss();
                GlobalBus.getBus().post(new Events.FragmentFragmentMessage("我是TwoFragment传到OneFragment的数据"));
                //startActivity(new Intent(getActivity(), MainActivity.class));
                //EventBus.getDefault().post(15);

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(getClass().getName(), "fragment： onActivityCreate");
        initView();
    }

    private void initView(){
        //设置MainActivity传过来的值
        if(activityFragmentMessage != null){
            tv_s.setText("TwoFragment："+activityFragmentMessage.getMessage());
        }
        //设置OneFragment传过来的值
        if(fragmentFragmentMessage != null){
            tv_s.setText("TwoFragment："+fragmentFragmentMessage.getMessage());
        }
    }

    /**
     * 根据生命周器，EventBus接收数据发生在View的实例化和绘制之前，那么如何完成刷新UI操作呢？
     * 错误的做法：在接收数据的同事去刷新UI（因为此时view还没有绘制）
     * 正确的做法：先接收数据，然后在onActivityCreated中，利用接收的数据去刷新UI
     * @param Message
     */

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent (Events.ActivityFragmentMessage Message) {
        if (Message != null) {
            activityFragmentMessage = Message;
        }
    }

    /**
     * 接收Events.FragmentFragmentMessage类型事件
     * @param message OneFragment传递的数据
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
            Toast.makeText(getActivity(), "TwoFragment："+activityActivityMessage.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消注册
    }
}

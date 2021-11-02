package com.zs.itking.eventbusputvaluedemo.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.zs.itking.eventbusputvaluedemo.R;
import com.zs.itking.eventbusputvaluedemo.base.eventbus.Events;
import com.zs.itking.eventbusputvaluedemo.base.eventbus.GlobalBus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * created by on 2021/11/2
 * 描述：
 *
 * @author ZSAndroid
 * @create 2021-11-02-13:20
 */
public class TwoActivity extends BaseActivity {

    private static String data = "我是TwoActivity传到MainActivity的数据";

    TextView tv_two_text;

    private static Events.ActivityActivityMessage activityActivityMessage;
    @Override
    public int bindLayout() {
        return R.layout.activity_two;
    }

    @Override
    public boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void initView() {
        tv_two_text = findViewById(R.id.tv_two_text);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        /* 1.本类实体对象数据库，设置到TextView上 */
        if(activityActivityMessage != null){
            tv_two_text.setText(activityActivityMessage.getMessage());
        }
        /* 2.TwoActivity向MainActivity发送串字符串文本数据 */
        GlobalBus.getBus().post(data);
    }

    /**
     * 接收Events.ActivityActivityMessage类型事件
     * @param message
     */
    @Subscribe(sticky = true)
    public void onEventMainThread(Events.ActivityActivityMessage message) {
        //接收MainActivity事件携带的实体数据，赋值给我本来的实体对象
        if (message != null) {
            activityActivityMessage = message;
        }
    }
}


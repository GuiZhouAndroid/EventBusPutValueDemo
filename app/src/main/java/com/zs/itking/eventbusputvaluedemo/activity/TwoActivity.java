package com.zs.itking.eventbusputvaluedemo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zs.itking.eventbusputvaluedemo.MyShop;
import com.zs.itking.eventbusputvaluedemo.R;
import com.zs.itking.eventbusputvaluedemo.base.AnyEventType;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * created by on 2021/11/2
 * 描述：
 *
 * @author ZSAndroid
 * @create 2021-11-02-13:20
 */
public class TwoActivity extends BaseActivity {

    private static String data = "这是来自TwoActivity的返回数据";

    TextView tv_two_text;
    private static AnyEventType anyEventType;
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
        if(anyEventType != null){
            tv_two_text.setText(anyEventType.getMsg());
        }
        /* 2.TwoActivity向MainActivity发送串字符串文本数据 */
        EventBus.getDefault().post(data);
    }


    @Subscribe(sticky = true)
    public void onEventMainThread(AnyEventType event) {
        //接收MainActivity事件携带的实体数据，赋值给我本来的实体对象
        if (event != null) {
            anyEventType = event;
        }
    }

}


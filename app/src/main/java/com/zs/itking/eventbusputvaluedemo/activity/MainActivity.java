package com.zs.itking.eventbusputvaluedemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.zs.itking.eventbusputvaluedemo.R;
import com.zs.itking.eventbusputvaluedemo.adapter.MyViewPagerAdapter;
import com.zs.itking.eventbusputvaluedemo.base.AnyEventType;
import com.zs.itking.eventbusputvaluedemo.fragment.OneFragment;
import com.zs.itking.eventbusputvaluedemo.fragment.TwoFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * created by on 2021/11/2
 * 描述：Activity 与 Fragment 通过EventBus传值
 *
 * @author ZSAndroid
 * @create 2021-11-02-11:20
 */
public class MainActivity extends BaseActivity {

    private OneFragment oneFragment;
    private TwoFragment twoFragment;
    private AnyEventType anyEventType;
    private String returnData;
    ViewPager main_vp_content;
    private TextView tv_one_return;

    /**
     * 四个主功能Fragment界面
     */
    public Fragment[] fragments = null;
    /**
     * 声明Fragment集合，ViewPager适配器遍历绑定数组fragments
     */
    private List<Fragment> fragmentList;

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
        main_vp_content = findViewById(R.id.main_vp_content);
        tv_one_return = findViewById(R.id.tv_one_return);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initViewPager();
    }

    public void but(View view) {
        /* 1.Activity传Activity传值 */
        startActivity(new Intent(MainActivity.this, TwoActivity.class));
        EventBus.getDefault().postSticky(new AnyEventType("我是One传输的数据"));

        /* 2.Activity传Fragment传值(MainActivity中的碎片) */
        addTwoFragment(new AnyEventType("MainActivity传输的数据"));

    }

    /**
     * 向OneFragment传值
     * @param anyEventType 实体数据
     */
    private void addOneFragment(AnyEventType anyEventType){
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

    /**
     * 向TwoFragment传值
     * @param anyEventType 实体数据
     */
    private void addTwoFragment(AnyEventType anyEventType){
        if(isFinishing()){
            return;
        }
        if(twoFragment != null){
            return;
        }
        twoFragment = twoFragment.newInstance();//在实例MyFragment时，便注册EventBus，保证在post前得到register
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_show, twoFragment).commitAllowingStateLoss();
        EventBus.getDefault().post(anyEventType);//向Fragment发送数据, 注意post操作应该放在MyFragment实例之后，即先register
    }

    /**
     * 初始化ViewPager，适配Fragment页面
     */
    private void initViewPager() {
        fragmentList = new ArrayList<>();
        //创建Fragment类型的数组，适配ViewPager，添加四个功能页
        fragments = new Fragment[]{new OneFragment(),new TwoFragment(),new TwoFragment(),new TwoFragment()};
        fragmentList.addAll(Arrays.asList(fragments));
        //ViewPager设置MyAdapter适配器，遍历List<Fragment>集合，填充Fragment页面
        main_vp_content.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), fragments, fragmentList));
        main_vp_content.setCurrentItem(0);//默认第一页
        main_vp_content.setOffscreenPageLimit(fragmentList.size());//viewPager单次预加载Fragment页数
        main_vp_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //选择新页面时调用
            @Override
            public void onPageSelected(int position) {
                main_vp_content.setCurrentItem(position);
            }

            //当滚动状态改变时调用，用于发现用户何时开始拖动
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private AnyEventType initDat1a(){
        anyEventType = new AnyEventType("我是One传输的数据");
        return anyEventType;
    }

    /**
     * 不可省略注解，否则会crash，不接收事件也必须有
     * @param data
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String data) {
        //接收TwoActivity传来字符串类型的文本数据， 并设置到TextView上
        if (data != null) {
            returnData = data;
            tv_one_return.setText(returnData);
        }
    }
}
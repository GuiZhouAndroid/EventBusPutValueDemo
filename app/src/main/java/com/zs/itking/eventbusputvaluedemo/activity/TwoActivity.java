package com.zs.itking.eventbusputvaluedemo.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.zs.itking.eventbusputvaluedemo.base.AnyEventType;
import com.zs.itking.eventbusputvaluedemo.adapter.MyViewPagerAdapter;
import com.zs.itking.eventbusputvaluedemo.fragment.OneFragment;
import com.zs.itking.eventbusputvaluedemo.R;
import com.zs.itking.eventbusputvaluedemo.fragment.TwoFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * created by on 2021/11/2
 * 描述：
 *
 * @author ZSAndroid
 * @create 2021-11-02-13:20
 */
public class TwoActivity extends BaseActivity {

    ViewPager mVp_content;

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
        return R.layout.activity_two;
    }

    @Override
    public boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void initView() {
        mVp_content = findViewById(R.id.vp_content);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initViewPager();
    }

    private void initViewPager() {
        fragmentList = new ArrayList<>();
        //创建Fragment类型的数组，适配ViewPager，添加四个功能页
        fragments = new Fragment[]{new OneFragment(),new TwoFragment()};
        fragmentList.addAll(Arrays.asList(fragments));
        //ViewPager设置MyAdapter适配器，遍历List<Fragment>集合，填充Fragment页面

        mVp_content.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), fragments, fragmentList));
        mVp_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //选择新页面时调用
            @Override
            public void onPageSelected(int position) {
                mVp_content.setCurrentItem(position);
                //mBottomBar.selectTabAtPosition(position, true);
            }

            //当滚动状态改变时调用，用于发现用户何时开始拖动
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Subscribe(sticky = true)
    public void onEventMainThread(AnyEventType event) {
        String msg = "收到了消息：" + event.getMsg();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

}


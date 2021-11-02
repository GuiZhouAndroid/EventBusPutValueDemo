package com.zs.itking.eventbusputvaluedemo.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * created by on 2021/11/2
 * 描述：
 *
 * @author ZSAndroid
 * @create 2021-11-02-13:29
 */
public class MyViewPagerAdapter extends FragmentStatePagerAdapter {
    /** 四个主功能Fragment界面 */
    public Fragment[] fragments =null;
    /** 创建Fragment集合，ViewPager适配器遍历绑定数组fragments*/
    public List<Fragment> fragmentList =null;

    public MyViewPagerAdapter(FragmentManager fm) {
        super(fm);

    }
    public MyViewPagerAdapter(@NonNull FragmentManager fm, Fragment[] fragments, List<Fragment> fragmentList) {
        super(fm);
        this.fragments = fragments;
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}

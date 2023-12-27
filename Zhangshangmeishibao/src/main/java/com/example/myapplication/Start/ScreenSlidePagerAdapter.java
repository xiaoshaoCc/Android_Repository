package com.example.myapplication.Start;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myapplication.Start.OnBoardingFragment1;
import com.example.myapplication.Start.OnBoardingFragment2;
import com.example.myapplication.Start.OnBoardingFragment3;

//页面适配器
public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private  static int NUM_PAGES=3;//三个加载片段
    public ScreenSlidePagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                OnBoardingFragment1 tab1=new OnBoardingFragment1();
                return  tab1;
            case 1:
                OnBoardingFragment2 tab2=new OnBoardingFragment2();
                return  tab2;
            case 2:
                OnBoardingFragment3 tab3=new OnBoardingFragment3();
                return  tab3;
        }
        return null;
    }


    @Override
    public int getCount() {
        return NUM_PAGES;//返回片段数
    }
}

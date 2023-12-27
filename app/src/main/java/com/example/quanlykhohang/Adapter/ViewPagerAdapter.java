package com.example.quanlykhohang.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.quanlykhohang.Fragments.NhapFragment;
import com.example.quanlykhohang.Fragments.TonFragment;
import com.example.quanlykhohang.Fragments.XuatFragment;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new NhapFragment();
            case 1:
                return new XuatFragment();
            case 2:
                return new TonFragment();
            default:
                return new NhapFragment();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title="";
        switch (position){
            case 0:
                title="Nhập";
                break;
            case 1:
                title="Xuất";
                break;
            case 2:
                title="Tồn";
                break;
        }
        return title;
    }
}

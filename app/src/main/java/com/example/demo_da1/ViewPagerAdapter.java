package com.example.demo_da1;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.demo_da1.Fragment.AccountFragment;
import com.example.demo_da1.Fragment.DinhDuongFragment;
import com.example.demo_da1.Fragment.HomeFragment;
import com.example.demo_da1.Fragment.TieuDiemFragment;
import com.example.demo_da1.Fragment.MevaBeFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new TieuDiemFragment();
            case 2:
                return new MevaBeFragment();
            case 3:
                return new DinhDuongFragment();

            case 4:
                return new AccountFragment();
            default:
                return new HomeFragment();
        }

    }

    @Override
    public int getCount() {
        return 5;
    }
}

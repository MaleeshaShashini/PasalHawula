package com.s23010667.pasalhawula;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyViewPagerAdapter extends FragmentStateAdapter {

    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PhysicalFormFragment();
            case 1:
                return new DigitalFormFragment();
            case 2:
                return new HumanFormFragment();
            default:
                return new PhysicalFormFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
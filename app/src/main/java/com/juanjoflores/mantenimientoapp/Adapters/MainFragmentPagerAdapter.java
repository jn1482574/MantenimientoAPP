package com.juanjoflores.mantenimientoapp.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Contabilidad 5 on 15/06/2017.
 */
public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> fragmettit = new ArrayList<>();

    public MainFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void addFragment(Fragment fragment, String Title) {
        fragments.add(fragment);
        fragmettit.add(Title);

    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmettit.get(position);
    }

    public void updateData(List<Fragment> rashisList, List<String> fragmettit){
       this.fragments = rashisList;
        this.fragmettit = fragmettit;

    }

    public Fragment getFragment(int position){
        return fragments.get(position);
    }

}
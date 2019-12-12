package ua.lviv.iot.andriy_popov.mobiledev;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class TabAdapter extends FragmentPagerAdapter {

    private final static int PAGE_COUNT = 3;
    private String[] tabTitles = new String[]{"Games", "Blank", "Profile"};

    TabAdapter(FragmentManager fm, Context context) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ListFragment();
            case 1:
                return new BlankFragment();
            case 2:
                return new ProfileFragment();
        }
        return new ListFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
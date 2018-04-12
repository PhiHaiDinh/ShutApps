package pp_ss2017.controllingapps.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import pp_ss2017.controllingapps.fragments.AppListFragment;
import pp_ss2017.controllingapps.fragments.BlackListFragment;

/**
 * Created by DucGiang on 28.04.2017.
 *
 * Dieser Adapter ist für die TabView zuständig.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumberTabs;

    public PagerAdapter(FragmentManager fragmentManager, int numberTabs) {
        super(fragmentManager);
        this.mNumberTabs = numberTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return (new AppListFragment());
            case 1:
                return (new BlackListFragment());
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumberTabs;
    }
}

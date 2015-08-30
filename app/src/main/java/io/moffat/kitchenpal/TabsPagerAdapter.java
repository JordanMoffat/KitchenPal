package io.moffat.kitchenpal;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;



/**
 * Created by Jordan on 29/08/2015.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter{
    private static final int NO_OF_TABS = 2;

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new MainListFrag();
            case 1:
                return new ShoppingListFrag();
        }

        return null;
    }

    @Override
    public int getCount() {
        return NO_OF_TABS;
    }
}

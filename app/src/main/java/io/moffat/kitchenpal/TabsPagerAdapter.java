package io.moffat.kitchenpal;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;



/**
 * Created by Jordan on 29/08/2015.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter{
   int mNumofTabs;

    public TabsPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumofTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new MainListFrag();
            case 1:
                return new ShoppingListFrag();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumofTabs;
    }
}

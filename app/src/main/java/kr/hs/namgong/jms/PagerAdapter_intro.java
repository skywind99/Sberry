package kr.hs.namgong.jms;

/**
 * Created by android on 2016-11-10.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class PagerAdapter_intro extends FragmentStatePagerAdapter {
    int mNumOfTabs;


    public PagerAdapter_intro(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabFragment1_intro tab1 = new TabFragment1_intro();
                return tab1;
            case 1:
                TabFragment2_intro tab2 = new TabFragment2_intro();
                return tab2;
            case 2:
                TabFragment3_intro tab3 = new TabFragment3_intro();
                return tab3;

            case 3:
                TabFragment4_intro tab4 = new TabFragment4_intro();
                return tab4;
            case 4:
                TabFragment5 tab5 = new TabFragment5();
                return tab5;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

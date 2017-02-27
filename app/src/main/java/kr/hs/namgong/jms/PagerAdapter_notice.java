package kr.hs.namgong.jms;

/**
 * Created by android on 2016-11-10.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class PagerAdapter_notice extends FragmentStatePagerAdapter {
    int mNumOfTabs;


    public PagerAdapter_notice(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabFragment1_notice tab1 = new TabFragment1_notice();
                return tab1;
            case 1:
                TabFragment2_notice tab2 = new TabFragment2_notice();
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

package de.malte.chatapp.newConversation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Malte on 18.03.14.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        /*switch (index) {
            case 0:
                // Top Rated fragment activity
                return new ContactPickerFragment();
            case 1:
                // Games fragment activity
                return new ManualFragment();
        }*/

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new Fragment();
            case 1:
                // Games fragment activity
                return new Fragment();
        }

        return null;
    }

    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }
}

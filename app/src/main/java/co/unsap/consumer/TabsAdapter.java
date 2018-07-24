package co.unsap.consumer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import co.unsap.consumer.fragments.DemoFragment;
import co.unsap.consumer.fragments.HomeFragment;
import co.unsap.consumer.fragments.ReferFriendFragment;
import co.unsap.consumer.fragments.ServiceRequestsFragment;
import co.unsap.consumer.fragments.SettingsFragment;

/**
 * Created by mac on 3/18/17.
 */

public class TabsAdapter extends FragmentStatePagerAdapter {




    public TabsAdapter(FragmentManager fm, HomeActivity activity) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {


    switch (position){


        case 0:{
            HomeFragment demoFragment = HomeFragment.newInstance(position);

            return demoFragment;

        }
        case 1:{


            ServiceRequestsFragment serviceRequestsFragment = ServiceRequestsFragment.newInstance(position);
            return serviceRequestsFragment;
        }


        case 2:{


            NotificationFragment notificationFragment = NotificationFragment.newInstance(position);
            return notificationFragment;
        }

        case 3:{
            SettingsFragment settingsFragment  = SettingsFragment.newInstance(position);
            return settingsFragment;
        }

        default: {
            HomeFragment demoFragment = HomeFragment.newInstance(position);

            return demoFragment;
        }


    }




    }

    @Override
    public int getCount() {
        return 5;
    }
}

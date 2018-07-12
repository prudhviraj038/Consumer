package co.unsap.consumer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import co.unsap.consumer.fragments.DemoFragment;
import co.unsap.consumer.fragments.HomeFragment;
import co.unsap.consumer.fragments.ServiceRequestsFragment;

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

        default: {
            HomeFragment demoFragment = HomeFragment.newInstance(position);

            return demoFragment;
        }


    }




    }

    @Override
    public int getCount() {
        return 2;
    }
}

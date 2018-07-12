package co.unsap.consumer;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mac on 6/26/18.
 */

public class TutorialActivity extends AppCompatActivity {

    ViewPager vp_tutorial;
    FragmentPagerAdapter adapterViewPager;
    Button btn_signup,btn_continue;
    TextView tv_skip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        if(getSupportActionBar()!=null)
        getSupportActionBar().hide();
        vp_tutorial = (ViewPager) findViewById(R.id.vp_tutorial);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vp_tutorial.setAdapter(adapterViewPager);

        btn_signup = (Button) findViewById(R.id.btn_signup);
        btn_continue = (Button) findViewById(R.id.btn_contimue);

        tv_skip = (TextView) findViewById(R.id.tv_skip);

        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TutorialActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });




        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TutorialActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });



        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(vp_tutorial.getCurrentItem()==adapterViewPager.getCount()-1){

                    Intent intent = new Intent(TutorialActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();

                }else{

                    vp_tutorial.setCurrentItem(vp_tutorial.getCurrentItem()+1);
                }

            }
        });

    }




    public static class FirstFragment extends Fragment {
        // Store instance variables
        private String title;
        private String message;
        private int resId;
        private int page;

        // newInstance constructor for creating fragment with arguments
        public static FirstFragment newInstance(int page, int resId,String title,String message) {
            FirstFragment fragmentFirst = new FirstFragment();
            Bundle args = new Bundle();
            args.putInt("someInt", page);
            args.putString("someTitle", title);
            args.putString("someMessage", message);
            args.putInt("someResource", resId);
            fragmentFirst.setArguments(args);
            return fragmentFirst;
        }

        // Store instance variables based on arguments passed
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            page = getArguments().getInt("someInt", 0);
            title = getArguments().getString("someTitle");
            message = getArguments().getString("someMessage");
            resId = getArguments().getInt("someResource");
        }

        // Inflate the view for the fragment based on layout XML
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_tutorial, container, false);

            ImageView imageView = (ImageView)view.findViewById(R.id.img_tutorial);
            imageView.setImageResource(resId);

            TextView tvLabel = (TextView) view.findViewById(R.id.heading_tutorial);
            tvLabel.setText(title);


            TextView tvMsg = (TextView) view.findViewById(R.id.message_tutorial);
            tvMsg.setText(message);

            return view;
        }
    }




    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 4;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return FirstFragment.newInstance(0, R.drawable.t_two,"Want to complete your tasks?","Choose from verified professionals, ready to serve you");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return FirstFragment.newInstance(1,R.drawable.t_three ,"Just tell us what you need","and get customized quotes from the best matched professional around you");
                case 2: // Fragment # 1 - This will show SecondFragment
                    return FirstFragment.newInstance(2, R.drawable.t_four,"Compare Profiles & Connect","with chosen professional via chat or call, finalize details & hire");
                case 3: // Fragment # 1 - This will show SecondFragment
                    return FirstFragment.newInstance(3, R.drawable.t_five,"Relax, while your task get done","& get enough time to do what you love. So lets get started now");
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }

}

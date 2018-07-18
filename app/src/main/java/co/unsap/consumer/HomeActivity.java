package co.unsap.consumer;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mac on 7/2/18.
 */

public class HomeActivity extends AppCompatActivity implements ProgressInterface{

    private DrawerLayout mDrawerLayout;
    TextView page_title,btn_edit;
    TextView tv_name,tv_mobile;

    LinearLayout menu_btn,back_btn;
    private ActionBarDrawerToggle mDrawerToggle;

    String temp_username;
    LinearLayout drawerView;
    RelativeLayout mainView;

    private TabsAdapter tabsAdapter;
    private ViewPager mViewPager;

    LinearLayout progress_bar;

    private void openNavigation(){

        if(mDrawerLayout.isDrawerOpen(GravityCompat.START))

            mDrawerLayout.closeDrawer(GravityCompat.START,true);
        else
            mDrawerLayout.openDrawer(GravityCompat.START,true);

    }


    private void setupActionBar() {
//set action bar
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.action_bar_title,null);

        page_title = (TextView) v.findViewById(R.id.page_title);

        menu_btn = (LinearLayout) v.findViewById(R.id.btn_menu_container);
        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNavigation();
            }
        });

        back_btn = (LinearLayout) v.findViewById(R.id.btn_back_container);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_edit = (TextView) v.findViewById(R.id.tv_edit);

        getSupportActionBar().setCustomView(v, layoutParams);
        Toolbar parent = (Toolbar) v.getParent();

        parent.setContentInsetsAbsolute(0, 0);


            }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String locale = getResources().getConfiguration().locale.getCountry();
        Log.e("country",locale);
        progress_bar = (LinearLayout) findViewById(R.id.progress_holder);

        tv_name = (TextView) findViewById(R.id.tv_name);
        temp_username = Constants.capitalizeString(SessionManager.getUserName(this));
        tv_name.setText(temp_username);


        tv_mobile = (TextView) findViewById(R.id.tv_mobile);
        tv_mobile.setText(SessionManager.getUSER_mobile(this));




        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        drawerView = (LinearLayout) findViewById(R.id.drawerView);
        mainView = (RelativeLayout) findViewById(R.id.mainView);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, null, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mainView.setTranslationX(slideOffset * drawerView.getWidth());
                mDrawerLayout.bringChildToFront(drawerView);
                mDrawerLayout.requestLayout();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        setupHeader();

        ArrayList<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("My Favorites","",R.drawable.ic_myfav));
        menuItems.add(new MenuItem("Service Requests","",R.drawable.ic_servicerequests));
        menuItems.add(new MenuItem("Notifications","",R.drawable.ic_notifications));
        menuItems.add(new MenuItem("Refer a Friend","",R.drawable.ic_refer));
        menuItems.add(new MenuItem("Settings","",R.drawable.ic_settings));


        MenuAdapter menuAdapter = new MenuAdapter(this,menuItems);

        ListView listView = (ListView) findViewById(R.id.menu_list);
        listView.setAdapter(menuAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                openNavigation();
                mViewPager.setCurrentItem(i);
                if (mViewPager.getCurrentItem()==0){
                    page_title.setText("Home");
                }
                else if (mViewPager.getCurrentItem()==1){
                    page_title.setText("Service Requests");
                }
                else if (mViewPager.getCurrentItem()==2){
                    page_title.setText("Notifications");
                }
                else if (mViewPager.getCurrentItem()==3){
                    page_title.setText("Refer a Friend");
                    String shareBody = "Look at this awesome app for aspiring service Providers! Use this Reference Code : https://www.u-snap.co/";
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Email Subject");
                    startActivity(sharingIntent);
                }
                else {
                    page_title.setText("Settings");
                }



            }
        });



        tabsAdapter = new TabsAdapter(getSupportFragmentManager(),this);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(tabsAdapter);



    }




    private void setupHeader(){

        back_btn.setVisibility(View.GONE);
        btn_edit.setVisibility(View.GONE);
        page_title.setText("Home");


    }


    @Override
    public void showProgress() {
        progress_bar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        progress_bar.setVisibility(View.GONE);
    }
}

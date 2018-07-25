package co.unsap.consumer;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import co.unsap.consumer.datamodels.ServiceProvider;

/**
 * Created by mac on 7/24/18.
 */

public class ProviderDetailActivity extends AppCompatActivity {

    ServiceProvider serviceProvider;
    TextView page_title,btn_edit;
    LinearLayout menu_btn,back_btn;

    ImageView provider_banner,provider;

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

            }
        });

        back_btn = (LinearLayout) v.findViewById(R.id.btn_back_container);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_edit = (TextView) v.findViewById(R.id.tv_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




            }

        });

        getSupportActionBar().setCustomView(v, layoutParams);
        Toolbar parent = (Toolbar) v.getParent();

        parent.setContentInsetsAbsolute(0, 0);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_detail);
        serviceProvider = (ServiceProvider) getIntent().getSerializableExtra("serviceProvider");
        Log.e("detail",serviceProvider.fullName);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        setupHeader();

        provider = (ImageView) findViewById(R.id.iv_provider);

        Picasso.with(this).load(serviceProvider.imageUrl).into(provider);

    }

    private void setupHeader(){

        back_btn.setVisibility(View.VISIBLE);
        menu_btn.setVisibility(View.GONE);
        btn_edit.setVisibility(View.VISIBLE);
        btn_edit.setText("Like");
        page_title.setText(serviceProvider.fullName);

    }


    private void likeProvider(){

    }

    private void unlikeProvider(){

    }




    }

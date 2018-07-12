package co.unsap.consumer;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import co.unsap.consumer.datamodels.SubService;

/**
 * Created by mac on 7/4/18.
 */

public class SubServiceSelectionActivity extends AppCompatActivity {

    TextView page_title,btn_edit;
    LinearLayout menu_btn,back_btn;
    ListView sub_services_listview;
    ArrayList<SubService> subServices;
    SubServicesAdapter subServicesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subserviceselection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        sub_services_listview = (ListView) findViewById(R.id.sub_services_listview);
        subServices = (ArrayList<SubService>) getIntent().getSerializableExtra("subServices");
        subServicesAdapter = new SubServicesAdapter(this,subServices);
        sub_services_listview.setAdapter(subServicesAdapter);
        sub_services_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                CustomBottomSheet customDialog = new CustomBottomSheet(SubServiceSelectionActivity.this,1,"hi","hello") {
                    @Override
                    public void yesClicked() {
                        navigateToNewServiceRequest("N",subServices.get(i).id,subServices.get(i).title);
                    }

                    @Override
                    public void noClicked() {
                        navigateToNewServiceRequest("N",subServices.get(i).id,subServices.get(i).title);
                    }
                };

            }
        });


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
        menu_btn.setVisibility(View.GONE);

        back_btn = (LinearLayout) v.findViewById(R.id.btn_back_container);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_edit = (TextView) v.findViewById(R.id.tv_edit);
        btn_edit.setText("Confirm");

        getSupportActionBar().setCustomView(v, layoutParams);
        Toolbar parent = (Toolbar) v.getParent();

        parent.setContentInsetsAbsolute(0, 0);


    }



    private void navigateToNewServiceRequest(String now_or_later,String subservice_id,String subservice_name){

        Intent intent = new Intent(this,NewServiceRequestActivity.class);
        intent.putExtra("service_id",getIntent().getStringExtra("service_id"));
        intent.putExtra("service_name",getIntent().getStringExtra("service_name"));
        intent.putExtra("sub_service_id",subservice_id);
        intent.putExtra("sub_service_name",subservice_name);
        intent.putExtra("now_or_later",now_or_later);
        startActivity(intent);
        finish();

    }




}

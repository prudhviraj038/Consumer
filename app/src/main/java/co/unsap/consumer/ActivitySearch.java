package co.unsap.consumer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import co.unsap.consumer.datamodels.Service;
import co.unsap.consumer.datamodels.ServiceProvider;

/**
 * Created by mac on 7/19/18.
 */

public class ActivitySearch extends AppCompatActivity {
    float LOCATION_REFRESH_DISTANCE = 1;
    long LOCATION_REFRESH_TIME = 100;
    Location current_location;
    ArrayList<ServiceProvider> serviceProviders = new ArrayList<>();
    ServiceProvidersAdapter serviceProvidersAdapter;
    ListView lv_search;
    LinearLayout progress_holder;
    TextView page_title,btn_edit;

    LinearLayout menu_btn,back_btn;

    private FusedLocationProviderClient mFusedLocationClient;

    Location mlocation;

    EditText et_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceprovider);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        setupHeader();
        hideSoftKeyboard();

        lv_search = (ListView) findViewById(R.id.lv_search);
        progress_holder = (LinearLayout) findViewById(R.id.progress_holder);
        progress_holder.setVisibility(View.GONE);

        serviceProvidersAdapter = new ServiceProvidersAdapter(this,serviceProviders);

        lv_search.setAdapter(serviceProvidersAdapter);


        et_search = (EditText) findViewById(R.id.et_search);



        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.e("permission","no");
            return;
        }
        Log.e("permission","yes");
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object

                            //Log.e("location",String.valueOf(location.getLatitude())+","+String.valueOf(location.getLongitude()));
                            //getServiceProviders(location);

                            mlocation = location;

                        }
                    }
                });




        et_search.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {


                hideSoftKeyboard();

                getServiceProviders(mlocation);


                return false;
            }
        });

    }






    private void getServiceProviders(Location location){


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = Constants.BASE_URL+"services/searchProviders";
        JSONObject parameters = new JSONObject();



        try {

            parameters.put("sessionId",SessionManager.getSessionId(this));
            parameters.put("searchString",et_search.getText().toString());
            JSONObject location_json = new JSONObject();
            location_json.put("longitude",String.valueOf(location.getLongitude()));
            location_json.put("latitude",String.valueOf(location.getLatitude()));
            parameters.put("location",location_json);



        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL,parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                hideProgress();

                try {
                    if(response.getString("error_code").equals("0")){

                        serviceProviders.clear();
                        serviceProvidersAdapter.notifyDataSetChanged();


                        JSONArray temp_services = response.getJSONArray("providersInfo");



                        for(int i=0;i<temp_services.length();i++) {

                            JSONObject jsonObject = temp_services.getJSONObject(i);
                            ServiceProvider serviceProvider = new ServiceProvider(jsonObject);
                            serviceProviders.add(serviceProvider);
                            Log.e("ss",jsonObject.toString());


                        }

                        serviceProvidersAdapter.notifyDataSetChanged();

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

                //showAlert("Success",response.toString());



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {



                error.printStackTrace();
                hideProgress();


            }
        });

        requestQueue.add(jsonObjectRequest);
        showProgress();


    }


    private void showProgress(){

        progress_holder.setVisibility(View.VISIBLE);

    }

    private void hideProgress(){
        progress_holder.setVisibility(View.GONE);
    }



    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Shows the soft keyboard
     */
    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
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
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();

            }

        });

        getSupportActionBar().setCustomView(v, layoutParams);
        Toolbar parent = (Toolbar) v.getParent();

        parent.setContentInsetsAbsolute(0, 0);


    }

    private void setupHeader(){

        menu_btn.setVisibility(View.GONE);
        back_btn.setVisibility(View.VISIBLE);
        btn_edit.setVisibility(View.GONE);
        page_title.setText("Search");


    }


}

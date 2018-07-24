package co.unsap.consumer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import co.unsap.consumer.fragments.MapFragment;

import static android.R.attr.bitmap;

/**
 * Created by mac on 7/10/18.
 */

public class NewServiceRequestActivity extends AppCompatActivity {


    TextView page_title,btn_edit;
    LinearLayout menu_btn,back_btn;
    LinearLayout ll_select_location;
    TextView tv_locations_search;
    String selected_lat,selected_long;
    MapFragment mapFragment;
    AddRequestBottomSheet addRequestBottomSheet;

    String service_id,sub_service_id,now_or_later;

    LinearLayout progress_holder;

    Image selected_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);
        progress_holder = (LinearLayout) findViewById(R.id.progress_holder);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        ll_select_location = (LinearLayout) findViewById(R.id.ll_select_location);
        tv_locations_search = (TextView) findViewById(R.id.locations_search);
        ll_select_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(NewServiceRequestActivity.this);
                    startActivityForResult(intent, 1);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }

            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("on","result");

        if (requestCode == 1 ) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i("tag", "Place: " + place.getAddress());
                tv_locations_search.setText(place.getAddress());
                selected_lat = String.valueOf(place.getLatLng().latitude);
                selected_long = String.valueOf(place.getLatLng().longitude);
                mapFragment.add_map_point(selected_lat,selected_long);
                //showAddRequestDailog();

                searchForServiceProviders();

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i("tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
       else if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            // Get a list of picked images
           // List<Image> images = ImagePicker.getImages(data);
            // or get a single image only
            Image image = ImagePicker.getFirstImageOrNull(data);
            selected_image = image;
            //addRequestBottomSheet.updateImages(image);

        }


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
        page_title.setText("New Service Request");

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
        btn_edit.setVisibility(View.GONE);

        getSupportActionBar().setCustomView(v, layoutParams);
        Toolbar parent = (Toolbar) v.getParent();

        parent.setContentInsetsAbsolute(0, 0);


    }


    private void showAddRequestDailog(){

        addRequestBottomSheet = new AddRequestBottomSheet(this) {
            @Override
            public void yesClicked(String comments) {


                postServiceRequest(comments);

            }

            @Override
            public void noClicked() {

            }
        };
    }



    private String getTimeStamp(){

        SimpleDateFormat curFormater = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm", Locale.ENGLISH);
        String date = curFormater.format(new Date());
        return date;

    }



    private void searchForServiceProviders(){


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = Constants.BASE_URL+"services/getProvidersBySubService";
        JSONObject parameters = new JSONObject();


        try {
            parameters.put("sessionId",SessionManager.getSessionId(this));
            parameters.put("serviceId",getIntent().getStringExtra("service_id"));
            parameters.put("subServiceId",getIntent().getStringExtra("sub_service_id"));
            JSONObject location = new JSONObject();
            location.put("longitude",selected_long);
            location.put("latitude",selected_lat);
            parameters.put("location",location);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL,parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                hideProgress();

                Log.e("response",response.toString());


                try {


                    if(response.getString("error_code").equals("0")){

                        JSONArray jsonArray = response.getJSONArray("providersInfo");

                        if(jsonArray.length()==0){

                            showAlert("Error","Currently , we are not serving your area");

                        }else{

                            showAddRequestDailog();

                        }

                    }else{

                        showAlert("Error",response.getString("message"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                hideProgress();

                Log.e("error",error.getMessage());

               // showAlert("Success",error.getMessage());

            }
        });

        requestQueue.add(jsonObjectRequest);
        showProgress();




    }


    private void postServiceRequest(String comments){

        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = Constants.BASE_URL+"request/postRequest";
        JSONObject parameters = new JSONObject();
        try {


//                    {@"sessionId":sessionId, @"subject":requestInfo[@"comments"],
//                        @"message": requestInfo[@"comments"],
//                        @"serviceName": requestInfo[@"serviceName"],
//                        @"serviceId":requestInfo[@"serviceId"],
//                        @"subServiceId":requestInfo[@"subServiceId"],
//                        @"subServiceName": requestInfo[@"subServiceName"],
//                        @"serviceType":isServiceNow?@"N":@"L",
//                        @"receiverUserId": (requestInfo[@"receiverUserId"])?requestInfo[@"receiverUserId"]: @"",
//                        @"receiverName": (requestInfo[@"receiverName"])?requestInfo[@"receiverName"]: @"",
//                        @"longitude": requestInfo[@"longitude"],
//                        @"latitude": requestInfo[@"latitude"],
//                        @"zip": (requestInfo[@"postal_code"])?requestInfo[@"postal_code"]:@(0),
//                        @"city": (requestInfo[@"locality"])?requestInfo[@"locality"]: @"",
//                        @"state": (requestInfo[@"administrative_area_level_1"])?requestInfo[@"administrative_area_level_1"]: @"",
//                        @"address1":requestInfo[@"address1"], @"address2": @"",
//                        @"scheduleOn":isServiceNow?[self calculateTimeStamp]:scheduleOn.text};



            parameters.put("sessionId",SessionManager.getSessionId(NewServiceRequestActivity.this));
            parameters.put("subject",comments);
            parameters.put("message",comments);
            parameters.put("serviceName",getIntent().getStringExtra("service_name"));
            parameters.put("serviceId",getIntent().getStringExtra("service_id"));

            parameters.put("subServiceId",getIntent().getStringExtra("sub_service_id"));
            parameters.put("subServiceName",getIntent().getStringExtra("sub_service_name"));
            parameters.put("serviceType","N");
            parameters.put("receiverUserId","");
            parameters.put("receiverName","");
            parameters.put("longitude",selected_long);
            parameters.put("latitude",selected_lat);
            parameters.put("zip","abc123");
            parameters.put("city","abc123");
            parameters.put("state","abc123");
            parameters.put("address1",tv_locations_search.getText());
            parameters.put("scheduleOn",getTimeStamp());

            Log.e("data_to_send",parameters.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }




        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL,parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                hideProgress();

                Log.e("response",response.toString());


                try {


                    if(response.getString("error_code").equals("0")){


                        if(selected_image==null){

                            showAlert("Success",response.getString("message"));

                        }else{

                            uploadImage(response.getString("requestId"));

                        }




                    }else{


                        showAlert("Error",response.getString("message"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                hideProgress();

                Log.e("error",error.getMessage());

                // showAlert("Success",error.getMessage());

            }
        });

        requestQueue.add(jsonObjectRequest);
        showProgress();




    }




    private void showProgress(){

        progress_holder.setVisibility(View.VISIBLE);
    }

    private  void hideProgress(){
        progress_holder.setVisibility(View.GONE);
    }



    private  void showAlert(String tittle,String message) {


        CustomDialog customDialog = new CustomDialog(this,CustomDialog.SINGLE_BUTTON,tittle,message) {
            @Override
            public void yesClicked() {



            }

            @Override
            public void noClicked() {

            }
        };

    }



    private void uploadImage(String request_id){



            //getting the tag from the edittext


            //our custom volley request
            VolleyMultiPartRequest volleyMultipartRequest = new VolleyMultiPartRequest(Request.Method.POST, Constants.BASE_URL+"request/postRequestFile/"+SessionManager.getSessionId(this)+"/"+request_id,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                            try {
                                JSONObject obj = new JSONObject(new String(response.data));

                                //Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                               // Log.e("res",obj.toString());

                                if(obj.getString("error_code").equals("0")){

                                    showAlert("Success",obj.getString("message"));

                                }else{

                                    showAlert("Error",obj.getString("message"));
                                }


                                hideProgress();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                           // Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            showAlert("Error",error.getMessage());

                        }
                    }) {

                /*
                * If you want to add more parameters with the image
                * you can do it here
                * here we have only one parameter with the image
                * which is tags
                * */
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", "image");
                    params.put("filename", "imageName.jpg");
                    return params;
                }

                /*
                * Here we are passing image by renaming it with a unique name
                * */
                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();
                    long imagename = System.currentTimeMillis();
                    params.put("pic", new DataPart(imagename + ".png",getFileDataFromDrawable(selected_image.getPath())));
                    return params;
                }
            };

            //adding the request to volley
            Volley.newRequestQueue(this).add(volleyMultipartRequest);
            showProgress();



    }


    public byte[] getFileDataFromDrawable(String file_name) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeFile(file_name);
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();


    }

}

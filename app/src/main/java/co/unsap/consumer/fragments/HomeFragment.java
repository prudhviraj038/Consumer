package co.unsap.consumer.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.unsap.consumer.Constants;
import co.unsap.consumer.CustomDialog;
import co.unsap.consumer.ProgressInterface;
import co.unsap.consumer.R;
import co.unsap.consumer.ServicesAdapter;
import co.unsap.consumer.SessionManager;
import co.unsap.consumer.SubServiceSelectionActivity;
import co.unsap.consumer.datamodels.Service;
import co.unsap.consumer.datamodels.SubService;

/**
 * Created by mac on 7/3/18.
 */

public class HomeFragment extends Fragment  {

    ListView services_list;
    ArrayList<Service> services = new ArrayList<>();
    ArrayList<SubService> sub_services = new ArrayList<>();
    ServicesAdapter servicesAdapter;

    private ProgressInterface progressInterface;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            progressInterface = (ProgressInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + e.getMessage());
        }
    }


    public static HomeFragment newInstance(int someInt) {
        HomeFragment myFragment = new HomeFragment();

        Bundle args = new Bundle();
        args.putInt("someInt", someInt);
        myFragment.setArguments(args);

        return myFragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        services_list = (ListView) view.findViewById(R.id.services_listview);

        services = new ArrayList<Service>();


        servicesAdapter = new ServicesAdapter(getActivity(),services);
        services_list.setAdapter(servicesAdapter);
        //setHasOptionsMenu(true);
        getServices();

        services_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                            getSubServices(services.get(i).id,services.get(i).title);

                            }
        });


        return view;
    }







    private void getServices(){




        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String URL = Constants.BASE_URL+"services/getServices";
        JSONObject parameters = new JSONObject();

        //  @"email=%@&password=%@&deviceId=%@&deviceType=%@&deviceName=%@&deviceToken=%@&appType=%@",
        try {
            parameters.put("sessionId", SessionManager.getSessionId(getActivity()));
                    } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL,parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressInterface.hideProgress();

                Log.e("response",response.toString());

                try {
                    if(response.getString("error_code").equals("0")){

                        JSONArray temp_services = response.getJSONArray("services");

                        for(int i=0;i<temp_services.length();i++) {

                            JSONObject jsonObject = temp_services.getJSONObject(i);
                            Service temp = new Service(jsonObject.getString("name"), jsonObject.getString("serviceLogo"), jsonObject.getString("_id"));
                            services.add(temp);
                        }
                        servicesAdapter.notifyDataSetChanged();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //showAlert("Success",response.toString());



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressInterface.hideProgress();

                Log.e("error",error.getMessage());


            }
        });

        requestQueue.add(jsonObjectRequest);
        progressInterface.showProgress();


    }


    private void getSubServices(final String serviceId,final String serviceName){



        sub_services.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String URL = Constants.BASE_URL+"services/getSubServices";
        JSONObject parameters = new JSONObject();

        //  @"email=%@&password=%@&deviceId=%@&deviceType=%@&deviceName=%@&deviceToken=%@&appType=%@",
        try {
            parameters.put("sessionId", SessionManager.getSessionId(getActivity()));
            parameters.put("serviceId", serviceId);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL,parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressInterface.hideProgress();

                Log.e("response",response.toString());

                try {
                    if(response.getString("error_code").equals("0")){

                        JSONArray temp_services = response.getJSONArray("subServices");

                        for(int i=0;i<temp_services.length();i++) {

                            JSONObject jsonObject = temp_services.getJSONObject(i);
                            SubService temp = new SubService(jsonObject.getString("subServiceName"), jsonObject.getString("subServiceName"), jsonObject.getString("_id"));
                            sub_services.add(temp);
                        }


                        if(sub_services.size()>0){


                            Intent intent = new Intent(getActivity(), SubServiceSelectionActivity.class);
                            intent.putExtra("subServices",sub_services);
                            intent.putExtra("service_id",serviceId);
                            intent.putExtra("service_name",serviceName);
                            startActivity(intent);

                        }else{


                            CustomDialog customDialog = new CustomDialog(getActivity(),CustomDialog.SINGLE_BUTTON,"Error","No Subservices for this service") {
                                @Override
                                public void yesClicked() {

                                }

                                @Override
                                public void noClicked() {

                                }
                            };

                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //showAlert("Success",response.toString());



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressInterface.hideProgress();

                Log.e("error",error.getMessage());


            }
        });

        requestQueue.add(jsonObjectRequest);
        progressInterface.showProgress();


    }



}

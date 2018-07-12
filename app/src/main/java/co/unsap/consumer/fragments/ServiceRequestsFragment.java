package co.unsap.consumer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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
import co.unsap.consumer.ProgressInterface;
import co.unsap.consumer.R;
import co.unsap.consumer.ServiceRequestsAdapter;
import co.unsap.consumer.SessionManager;
import co.unsap.consumer.datamodels.Service;
import co.unsap.consumer.datamodels.ServiceRequest;

/**
 * Created by mac on 3/18/17.
 */

public class ServiceRequestsFragment extends Fragment {


    private ProgressInterface progressInterface;
    ArrayList<ServiceRequest> serviceRequests = new ArrayList<>();
    ListView service_request_list;
    ServiceRequestsAdapter serviceRequestsAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            progressInterface = (ProgressInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + e.getMessage());
        }
    }


    public static ServiceRequestsFragment newInstance(int someInt) {
        ServiceRequestsFragment myFragment = new ServiceRequestsFragment();

        Bundle args = new Bundle();
        args.putInt("someInt", someInt);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_requests, container, false);

        service_request_list = (ListView) view.findViewById(R.id.service_requests_listview);

        serviceRequestsAdapter = new ServiceRequestsAdapter(getActivity(),serviceRequests);

        service_request_list.setAdapter(serviceRequestsAdapter);

        getServiceRequests();

        return view;
    }


    private  void getServiceRequests(){


        serviceRequests.clear();

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String URL = Constants.BASE_URL+"request/getMyRequests";
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

                        JSONArray temp_services = response.getJSONArray("myRequests");

                        for(int i=0;i<temp_services.length();i++) {

                            JSONObject jsonObject = temp_services.getJSONObject(i);
                            ServiceRequest serviceRequest = new ServiceRequest(jsonObject);
                            serviceRequests.add(serviceRequest);



                        }
                        serviceRequestsAdapter.notifyDataSetChanged();

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

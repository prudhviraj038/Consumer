package co.unsap.consumer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
import java.util.List;

import co.unsap.consumer.Constants;
import co.unsap.consumer.NotificationAdapter;
import co.unsap.consumer.ProgressInterface;
import co.unsap.consumer.R;
import co.unsap.consumer.ServiceRequestsAdapter;
import co.unsap.consumer.SessionManager;
import co.unsap.consumer.datamodels.Notifications;
import co.unsap.consumer.datamodels.ServiceRequest;

/**
 * Created by mac on 3/18/17.
 */

public class NotificationFragment extends Fragment {


    private ProgressInterface progressInterface;
    ArrayList<Notifications> notifications = new ArrayList<>();
    ListView service_request_list,notify_list;

    NotificationAdapter notificationAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            progressInterface = (ProgressInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + e.getMessage());
        }
    }


    public static NotificationFragment newInstance(int someInt) {
        NotificationFragment myFragment = new NotificationFragment();

        Bundle args = new Bundle();
        args.putInt("someInt", someInt);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        notify_list = (ListView)view.findViewById(R.id.notification_recycler_fragment);
      //  service_request_list = (ListView) view.findViewById(R.id.service_requests_listview);

        notificationAdapter = new NotificationAdapter(getActivity(),notifications);

        notify_list.setAdapter(notificationAdapter);
       // service_request_list.setAdapter(notificationAdapter);

      getServiceRequests();

        return view;
    }


    private  void getServiceRequests(){


        notifications.clear();

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String URL = Constants.BASE_URL+"notification/getMyNotifications";
        JSONObject parameters = new JSONObject();

        //  @"email=%@&password=%@&deviceId=%@&deviceType=%@&deviceName=%@&deviceToken=%@&appType=%@",
        try {
            parameters.put("sessionId", SessionManager.getSessionId(getActivity()));
            parameters.put("appType","CONSUMER");
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

                        JSONArray temp_services = response.getJSONArray("notifications");

                        for(int i=0;i<temp_services.length();i++) {

                            JSONObject jsonObject = temp_services.getJSONObject(i);
                            Notifications serviceRequest = new Notifications(jsonObject);
                            notifications.add(serviceRequest);



                        }
                        notificationAdapter.notifyDataSetChanged();

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

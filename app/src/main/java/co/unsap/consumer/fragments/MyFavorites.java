package co.unsap.consumer.fragments;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import co.unsap.consumer.ActivitySearch;
import co.unsap.consumer.Constants;
import co.unsap.consumer.ProgressInterface;
import co.unsap.consumer.ProviderDetailActivity;
import co.unsap.consumer.R;
import co.unsap.consumer.ServiceProvidersAdapter;
import co.unsap.consumer.SessionManager;
import co.unsap.consumer.datamodels.ServiceProvider;

/**
 * Created by mac on 7/24/18.
 */

public class MyFavorites extends Fragment {
    private ProgressInterface progressInterface;
    ArrayList<ServiceProvider> serviceProviders = new ArrayList<>();
    ServiceProvidersAdapter serviceProvidersAdapter;
    ListView lv_search;


    public static MyFavorites newInstance(int someInt) {
        MyFavorites myFragment = new MyFavorites();

        Bundle args = new Bundle();
        args.putInt("someInt", someInt);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            progressInterface = (ProgressInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + e.getMessage());
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        lv_search = (ListView)view.findViewById(R.id.lv_search);
        lv_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity(),ProviderDetailActivity.class);
                intent.putExtra("serviceProvider",serviceProviders.get(i));
                startActivity(intent);

            }
        });
        serviceProvidersAdapter = new ServiceProvidersAdapter(getActivity(),serviceProviders);

        lv_search.setAdapter(serviceProvidersAdapter);


        //setHasOptionsMenu(true);

        getServiceProviders();

        return view;
    }



    private void getServiceProviders(){


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String URL = Constants.BASE_URL+"favorites/myFavorites";
        JSONObject parameters = new JSONObject();



        try {

            parameters.put("sessionId", SessionManager.getSessionId(getActivity()));



        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL,parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressInterface.hideProgress();


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
                progressInterface.hideProgress();


            }
        });

        requestQueue.add(jsonObjectRequest);
        progressInterface.showProgress();


    }


}

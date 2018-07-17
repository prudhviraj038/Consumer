package co.unsap.consumer.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

import co.unsap.consumer.Constants;
import co.unsap.consumer.LoginActivity;
import co.unsap.consumer.NotificationAdapter;
import co.unsap.consumer.ProgressInterface;
import co.unsap.consumer.R;
import co.unsap.consumer.SessionManager;
import co.unsap.consumer.datamodels.Notifications;

/**
 * Created by mac on 3/18/17.
 */

public class SettingsFragment extends Fragment {

    private ProgressInterface progressInterface;
    LinearLayout logout;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            progressInterface = (ProgressInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + e.getMessage());
        }
    }


    public static SettingsFragment newInstance(int someInt) {
        SettingsFragment myFragment = new SettingsFragment();

        Bundle args = new Bundle();
        args.putInt("someInt", someInt);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        logout = (LinearLayout)view.findViewById(R.id.logout_layout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManager.setSessionId(getContext(),"0");
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
     // getServiceRequests();

        return view;
    }

}

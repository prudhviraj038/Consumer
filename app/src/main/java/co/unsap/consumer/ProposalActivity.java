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
import android.widget.AdapterView;
import android.widget.LinearLayout;
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

import co.unsap.consumer.datamodels.Proposal;
import co.unsap.consumer.datamodels.Service;
import co.unsap.consumer.datamodels.ServiceRequest;

/**
 * Created by mac on 7/16/18.
 */

public class ProposalActivity extends AppCompatActivity {

    TextView page_title,btn_edit;
    LinearLayout menu_btn,back_btn;
    ServiceRequest serviceRequest;
    ProposalAdapter proposalAdapter;
    ArrayList<Proposal> proposals =new ArrayList<>();
    ListView proposals_list_view ;
    LinearLayout progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposal);
        progress_bar = (LinearLayout) findViewById(R.id.progress_holder);
        serviceRequest = (ServiceRequest) getIntent().getSerializableExtra("serviceRequest");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        setupHeader();
        proposalAdapter = new ProposalAdapter(this,proposals);
        proposals_list_view = (ListView) findViewById(R.id.lv_proposals_listview);
        proposals_list_view.setAdapter(proposalAdapter);
        proposals_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(ProposalActivity.this,ChatActivity.class);
                intent.putExtra("proposal",serviceRequest.proposals.get(i));
                startActivity(intent);

            }
        });


        getProposals();

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
        page_title.setText(serviceRequest.serviceName);

        menu_btn = (LinearLayout) v.findViewById(R.id.btn_menu_container);

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


    private void setupHeader(){

        menu_btn.setVisibility(View.GONE);
        back_btn.setVisibility(View.VISIBLE);
        btn_edit.setVisibility(View.GONE);




    }


    private void getProposals(){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = Constants.BASE_URL+"proposal/getMyRequestProposals";
        JSONObject parameters = new JSONObject();
        try {
            parameters.put("sessionId", SessionManager.getSessionId(this));
            parameters.put("requestId", serviceRequest._id);
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

                        JSONArray temp_proposals = response.getJSONArray("proposals");

                        for(int i=0;i<temp_proposals.length();i++) {

                            JSONObject jsonObject = temp_proposals.getJSONObject(i);
                            Proposal temp = new Proposal(jsonObject,"proposal");
                            proposals.add(temp);

                        }

                        proposalAdapter.notifyDataSetChanged();


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {



                Log.e("error",error.getMessage());
                hideProgress();


            }
        });

        requestQueue.add(jsonObjectRequest);
        showProgress();



    }

    public void showProgress() {
        progress_bar.setVisibility(View.VISIBLE);

    }


    public void hideProgress() {
        progress_bar.setVisibility(View.GONE);

    }

}



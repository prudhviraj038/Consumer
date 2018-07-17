package co.unsap.consumer;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.unsap.consumer.datamodels.Proposal;
import co.unsap.consumer.datamodels.ProposalComments;

/**
 * Created by mac on 7/14/18.
 */

public class ChatActivity extends AppCompatActivity {

    TextView page_title,btn_edit;
    LinearLayout menu_btn,back_btn;
    ImageView send_btn,iv_chat;
    TextView tv_chat_name,tv_chat_ratings;
    Proposal proposal;
    EditText et_chat_message;
    ListView lv_chat;
    LinearLayout progress_bar;
    ChatAdapter chatAdapter;
    ArrayList<ProposalComments> proposalCommentses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        progress_bar = (LinearLayout) findViewById(R.id.progress_holder);
        proposal = (Proposal) getIntent().getSerializableExtra("proposal");
        send_btn = (ImageView) findViewById(R.id.iv_send);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(et_chat_message.getText().toString().equals("")){

                }else{

                    sendComments(et_chat_message.getText().toString());

                }

                closeKeyBoard();

            }
        });
        iv_chat = (ImageView) findViewById(R.id.iv_chat);
        tv_chat_name = (TextView) findViewById(R.id.tv_chat_name);
        tv_chat_ratings = (TextView) findViewById(R.id.tv_chat_ratings);
        et_chat_message = (EditText) findViewById(R.id.et_chat_message);
        lv_chat = (ListView) findViewById(R.id.lv_chat);

        Picasso.with(this).load(proposal.proposer_profile_pic).into(iv_chat);
        tv_chat_name.setText(proposal.proposer_name);
        tv_chat_ratings.setText( proposal.proposer_reviews +" Reviews. Hired "+ Integer.parseInt(proposal.proposer_hired)+ " Times");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        setupHeader();

        chatAdapter = new ChatAdapter(this,proposalCommentses);
        lv_chat.setAdapter(chatAdapter);

        getComments();

    }


    private void closeKeyBoard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void setupHeader(){

        menu_btn.setVisibility(View.GONE);
        back_btn.setVisibility(View.VISIBLE);
        btn_edit.setVisibility(View.GONE);



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
        v.setBackground(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        page_title = (TextView) v.findViewById(R.id.page_title);

        page_title.setText(proposal.proposer_name);

        menu_btn = (LinearLayout) v.findViewById(R.id.btn_menu_container);
        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        back_btn = (LinearLayout) v.findViewById(R.id.btn_back_container);
        back_btn.setOnClickListener(    new View.OnClickListener() {
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


    private void getComments(){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = Constants.BASE_URL+"proposal/getRequestProposalComments";
        JSONObject parameters = new JSONObject();
        try {
            parameters.put("sessionId", SessionManager.getSessionId(this));
            parameters.put("proposalId", proposal.id);
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

                        JSONArray temp_proposals = response.getJSONArray("proposalComments");

                        proposalCommentses.clear();
                        //chatAdapter.notifyDataSetChanged();

                        for(int i=0;i<temp_proposals.length();i++) {

                            JSONObject jsonObject = temp_proposals.getJSONObject(i);
                            ProposalComments temp = new ProposalComments(jsonObject);

                            proposalCommentses.add(temp);

                        }

                        chatAdapter.notifyDataSetChanged();


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }





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



    public void showProgress() {
        progress_bar.setVisibility(View.VISIBLE);

    }


    public void hideProgress() {
        progress_bar.setVisibility(View.GONE);

    }


    private void sendComments(String message){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = Constants.BASE_URL+"proposal/addProposalCommentByConsumer";
        JSONObject parameters = new JSONObject();
        try {
            parameters.put("sessionId", SessionManager.getSessionId(this));
            parameters.put("proposalId", proposal.id);
            parameters.put("comment", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL,parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {



                Log.e("response",response.toString());

                try {
                    if(response.getString("error_code").equals("0")){

                        getComments();


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



}

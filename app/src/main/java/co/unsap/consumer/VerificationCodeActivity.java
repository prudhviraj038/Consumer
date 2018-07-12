package co.unsap.consumer;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mac on 7/2/18.
 */

public class VerificationCodeActivity extends AppCompatActivity {

    EditText et_code;
    LinearLayout progress_holder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        progress_holder = (LinearLayout) findViewById(R.id.progress_holder);

        if(getSupportActionBar()!=null)
            getSupportActionBar().hide();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();


        TextView tv_back = (TextView) findViewById(R.id.tv_back);
        TextView tv_verify_now = (TextView) findViewById(R.id.tv_verifynow);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });


        tv_verify_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verifyDevice(getIntent().getStringExtra("userId"),et_code.getText().toString(),SessionManager.getDeviceId(VerificationCodeActivity.this));

            }
        });


         et_code = (EditText) findViewById(R.id.et_code);


    }


    private  void  verifyDevice(String user_id, String code,String device_id){


        if(et_code.getText().length()!=6){

            showAlert("Error","Please enter six digits");
        }else{




            final RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = Constants.BASE_URL+"auth/validateDevice";
            JSONObject parameters = new JSONObject();

            try {
                parameters.put("userId",user_id);
                parameters.put("deviceId",device_id);
                parameters.put("twoFactorAuthCode",code);

            } catch (JSONException e) {
                e.printStackTrace();
            }


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL,parameters, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Log.e("response",response.toString());

                    hideProgress();

                    try {
                        if(response.getString("error_code").equals("0")){

                            showAlert("Success",response.getString("message"));

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

                    showAlert("Success",error.getMessage());

                }
            });

            requestQueue.add(jsonObjectRequest);

            showProgress();











        }




    }


    private  void showAlert(String tittle,String message) {


        CustomDialog customDialog = new CustomDialog(this,CustomDialog.SINGLE_BUTTON,tittle,message) {
            @Override
            public void yesClicked() {

                if(tittle.equals("Success")){
                    setResult(RESULT_OK);
                    finish();

                }


            }

            @Override
            public void noClicked() {

            }
        };



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
        View v = inflater.inflate(R.layout.actionbar_login,null);

        TextView tittle = (TextView)v.findViewById(R.id.tv_actionbar_tittle);
        tittle.setText("Verification");

        getSupportActionBar().setCustomView(v, layoutParams);
        Toolbar parent = (Toolbar) v.getParent();
        parent.setContentInsetsAbsolute(0, 0);

    }


    private void showProgress(){

        progress_holder.setVisibility(View.VISIBLE);

    }


    private void hideProgress(){


        progress_holder.setVisibility(View.GONE);
    }


}
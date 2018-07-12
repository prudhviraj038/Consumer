package co.unsap.consumer;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
 * Created by mac on 6/25/18.
 */

public class LoginActivity extends AppCompatActivity {

    EditText et_username,et_password;
    LinearLayout progress_holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progress_holder = (LinearLayout) findViewById(R.id.progress_holder);



        if(SessionManager.getDeviceId(this).equals("0")){

            SessionManager.setDeviceId(this,String.valueOf(System.currentTimeMillis()));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();

        Button btn = (Button) findViewById(R.id.btn_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(et_username.getText().toString().length()==0){
                    showAlert("Error","Please enter email");

                }else if(et_password.getText().toString().length()==0){
                    showAlert("Error","Please enter password");

                }else {

                        CallLogInService();

                }


            }
        });


        TextView hint = (TextView) findViewById(R.id.login_hitn);
        hint.setText(Html.fromHtml("New Here? <font color='#2196F3'>Create Your Account</font>"));
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();

            }
        });


        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);

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
        tittle.setText("Log In");

        getSupportActionBar().setCustomView(v, layoutParams);
        Toolbar parent = (Toolbar) v.getParent();
        parent.setContentInsetsAbsolute(0, 0);

            }



    private void CallLogInService(){


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = Constants.BASE_URL+"auth/login";
        JSONObject parameters = new JSONObject();

      //  @"email=%@&password=%@&deviceId=%@&deviceType=%@&deviceName=%@&deviceToken=%@&appType=%@",
        try {
            parameters.put("email",et_username.getText().toString());
            parameters.put("password",et_password.getText().toString());
            parameters.put("deviceId",SessionManager.getDeviceId(LoginActivity.this));
            parameters.put("deviceName","Android");
            parameters.put("deviceToken","abc123");
            parameters.put("appType","CONSUMER");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL,parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {



                Log.e("response",response.toString());


                try {
                    if(response.getString("error_code").equals("2") || response.getString("error_code").equals("3")){


                        addDevice(response.getString("userId"));


                    }else if(response.getString("error_code").equals("1")){

                        hideProgress();
                        showAlert("Error",response.getString("message"));

                    }else {

                        hideProgress();
                        SessionManager.setSessionId(LoginActivity.this,response.getString("sessionId"));
                        SessionManager.setUserName(LoginActivity.this,response.getJSONObject("userProfile").getString("firstName") +" "+response.getJSONObject("userProfile").getString("lastName") );
                        SessionManager.setUSER_mobile(LoginActivity.this,response.getJSONObject("userProfile").getString("phone"));
                        SessionManager.setUserImage(LoginActivity.this,response.getJSONObject("userProfile").getString("imageUrl"));

                        navigateToHomeActivity();
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


    private  void showAlert(String tittle,String message) {


        CustomDialog customDialog = new CustomDialog(this,CustomDialog.SINGLE_BUTTON,tittle,message) {
            @Override
            public void yesClicked() {

                if(tittle.equals("Success")){
                    navigateToHomeActivity();
                }


            }

            @Override
            public void noClicked() {

            }
        };

    }



    private void navigateToHomeActivity(){

        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();

    }




    private void navigateToVerificastion(String userId){

        Intent intent = new Intent(this,VerificationCodeActivity.class);
        intent.putExtra("userId",userId);
        startActivityForResult(intent, 100);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 100) {
            if(resultCode == Activity.RESULT_OK){
                CallLogInService();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//on

    private void addDevice(final String user_id){

        Log.e("response","device");


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = Constants.BASE_URL+"auth/addDevice";
        JSONObject parameters = new JSONObject();

        //  @"email=%@&password=%@&deviceId=%@&deviceType=%@&deviceName=%@&deviceToken=%@&appType=%@",
        try {
            parameters.put("userId",user_id);
            parameters.put("deviceId",SessionManager.getDeviceId(this));
            parameters.put("deviceName","Android");
            parameters.put("deviceToken","abc123");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL,parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                hideProgress();

                Log.e("response",response.toString());

               // showAlert("Success",response.toString());

                navigateToVerificastion(user_id);




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


    }




    private void showProgress(){

        progress_holder.setVisibility(View.VISIBLE);
    }

    private  void hideProgress(){
        progress_holder.setVisibility(View.GONE);
    }

}

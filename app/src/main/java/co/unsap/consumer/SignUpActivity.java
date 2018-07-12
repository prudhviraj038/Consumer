package co.unsap.consumer;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import co.unsap.consumer.FontUtilities.CustomTextView;

/**
 * Created by mac on 6/25/18.
 */

public class SignUpActivity extends AppCompatActivity {

    EditText et_firstname,et_lastname,et_mobile,et_username,et_password,et_confirm_password;
    Button  btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();

        Button btn = (Button) findViewById(R.id.btn_signup);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(et_firstname.getText().toString().length()==0){

                    showAlert("Error","Please enter firstname");
                }else if(et_lastname.getText().toString().length()==0){
                    showAlert("Error","Please enter lastname");

                }else if(et_mobile.getText().toString().length()==0){
                    showAlert("Error","Please enter mobile");

                }else if(et_username.getText().toString().length()==0){
                    showAlert("Error","Please enter email");

                }else if(et_password.getText().toString().length()==0){
                    showAlert("Error","Please enter password");

                }else if(et_confirm_password.getText().toString().length()==0){
                    showAlert("Error","Please enter confirm password");

                }else if(!et_confirm_password.getText().toString().equals(et_password.getText().toString())){
                    showAlert("Error","Please enter same password");
                }else {

                    CallSignUpService();

                }



            }
        });

        et_firstname = (EditText) findViewById(R.id.et_firstname);
        et_lastname = (EditText) findViewById(R.id.et_lastname);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_confirm_password = (EditText) findViewById(R.id.et_confirm_password);


        TextView hint = (TextView) findViewById(R.id.login_hitn);
        hint.setText(Html.fromHtml("Have Account? <font color='#2196F3'>Login here</font>"));
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });




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
        CustomTextView tittle = (CustomTextView)v.findViewById(R.id.tv_actionbar_tittle);
        tittle.setText("Sign Up");
        getSupportActionBar().setCustomView(v, layoutParams);
        Toolbar parent = (Toolbar) v.getParent();
        parent.setContentInsetsAbsolute(0, 0);

            }



    private void CallSignUpService(){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please wait loading");
        progressDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = Constants.BASE_URL+"auth/register";
        JSONObject parameters = new JSONObject();

        //@"email=%@&password=%@&confirmPassword=%@&firstName=%@&lastName=%@&phone=%@&deviceId=%@&deviceType=%@&deviceName=%@&countryCode=%@"
        try {
            parameters.put("email",et_username.getText().toString());
            parameters.put("password",et_password.getText().toString());
            parameters.put("confirmPassword",et_confirm_password.getText().toString());
            parameters.put("firstName",et_firstname.getText().toString());
            parameters.put("lastName",et_lastname.getText().toString());
            parameters.put("phone",et_mobile.getText().toString());
            parameters.put("deviceId",et_username.getText().toString());
            parameters.put("deviceType",et_username.getText().toString());
            parameters.put("deviceName",et_username.getText().toString());

            String locale = getResources().getConfiguration().locale.getCountry();
            String country_code = "1";
            if(locale.equals("IN")){
             country_code="91";
            }
            parameters.put("countryCode",country_code);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL,parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                Log.e("response",response.toString());

                try {


                    if(response.has("userId")){
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

                progressDialog.dismiss();

                Log.e("error",error.getMessage());

                showAlert("Error",error.getMessage());

            }
        });

        requestQueue.add(jsonObjectRequest);



    }


    private  void showAlert(final String tittle, String message) {


        CustomDialog customDialog = new CustomDialog(this,CustomDialog.SINGLE_BUTTON,tittle,message) {
            @Override
            public void yesClicked() {

                if(tittle.equals("Success")){

                    Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }


            }

            @Override
            public void noClicked() {

            }
        };

    }
}

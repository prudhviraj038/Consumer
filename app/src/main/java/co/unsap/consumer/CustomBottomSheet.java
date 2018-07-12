package co.unsap.consumer;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by mac on 7/4/18.
 */

public abstract class CustomBottomSheet extends Dialog implements
        View.OnClickListener,DialogInterface {

    public static int SINGLE_BUTTON = 0;
    public static int DOUBLE_BUTTON = 1;

    public Activity c;
    public Dialog d;
    public Button yes, no, cancel;
    public int alert_type = 0;

    String tittle,message;

    boolean atBottom = false;

   public CustomBottomSheet(Activity a, int alert_type , String tittle, String message) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.alert_type = alert_type;
        this.tittle = tittle;
        this.message = message;
         atBottom = true;
        show();


    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_bottom_sheet);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        cancel = (Button) findViewById(R.id.btn_cancel);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        cancel.setOnClickListener(this);

        if(alert_type==0){
            no.setVisibility(View.GONE);
            yes.setText("OK");
        }else{
            no.setVisibility(View.VISIBLE);

        }


        if(atBottom){
            getWindow().setGravity(Gravity.BOTTOM);

        }

        TextView tittle_tv = (TextView) findViewById(R.id.tv_tittle_dialog);
        TextView message_tv = (TextView) findViewById(R.id.tv_message_dialog);

        tittle_tv.setText(tittle);
        message_tv.setText(message);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setCancelable(false);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                yesClicked();
                break;
            case R.id.btn_no:
                noClicked();
                break;
            default:
                break;
        }
        dismiss();
    }
}
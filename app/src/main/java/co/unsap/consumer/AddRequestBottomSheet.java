package co.unsap.consumer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;

import java.io.File;
import java.util.List;

/**
 * Created by mac on 7/4/18.
 */

public abstract class AddRequestBottomSheet extends Dialog implements
        View.OnClickListener,AddRequestInterface {

    public static int SINGLE_BUTTON = 0;
    public static int DOUBLE_BUTTON = 1;

    public Activity c;
    public Dialog d;
    public Button yes, no ;

    public EditText et_comments;

    ImageView iv_add;


   public AddRequestBottomSheet(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;

       Log.e("comments","resd");
        show();


    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.addrequest_bottom_sheet);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        iv_add = (ImageView) findViewById(R.id.iv_add);

        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                ImagePicker.create(c)
                        .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
                        .folderMode(true) // folder mode (false by default)
                        .toolbarFolderTitle("Folder") // folder selection title
                        .toolbarImageTitle("Tap to select") // image selection title
                        .toolbarArrowColor(Color.BLACK) // Toolbar 'up' arrow color
                        .single() // single mode
                        .showCamera(true) // show camera or not (true by default)
                        .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                        .start(); // start image picker activity with request code



            }
        });

        et_comments = (EditText) findViewById(R.id.et_comments);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setCancelable(false);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                yesClicked(et_comments.getText().toString());
                break;
            case R.id.btn_no:
                noClicked();
                break;
            default:
                break;
        }
        dismiss();
    }



    public void updateImages(Image image){
        iv_add.setImageURI(Uri.fromFile(new File(image.getPath())));
    }

    }
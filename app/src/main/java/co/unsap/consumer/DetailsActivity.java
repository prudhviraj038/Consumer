package co.unsap.consumer;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

import co.unsap.consumer.datamodels.ServiceRequest;

public class DetailsActivity extends AppCompatActivity {
	ViewPager viewPager;

	ServiceRequest serviceRequest;

	TextView requestnumber,servicedetails,address,comments,assignedto;
	ImageView requestimage;

	TextView page_title,btn_edit;
	LinearLayout menu_btn,back_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		serviceRequest=(ServiceRequest) getIntent().getSerializableExtra("serviceRequest");
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		setupActionBar();
		setupHeader();


		requestnumber = (TextView)findViewById(R.id.request_number_text);
		servicedetails = (TextView)findViewById(R.id.service_details_text);
		address = (TextView)findViewById(R.id.address_text);
		comments = (TextView)findViewById(R.id.comments_text);
		assignedto = (TextView)findViewById(R.id.assignedto_text);

		requestimage = (ImageView)findViewById(R.id.request_image);
		viewPager = (ViewPager)findViewById(R.id.image_viewpager);


		Log.e("serviceRequest",serviceRequest.requestCode);
		displayServiceRequestData(serviceRequest);


		if(serviceRequest.images.size()>0)
		Picasso.with(this).load(serviceRequest.images.get(0).image_url).into(requestimage);

	}

	public void displayServiceRequestData(ServiceRequest serviceRequest){

		requestnumber.setText(serviceRequest.requestCode);
		servicedetails.setText(serviceRequest.serviceName+" > "+serviceRequest.subServiceName);
		address.setText(serviceRequest.address);
		comments.setText(serviceRequest.subject);
		if (serviceRequest.receiverName.equals("")){
			assignedto.setText("None");
		}
		else {
		assignedto.setText(serviceRequest.receiverName);
		}

		/*ArrayList<String> images = new ArrayList<>();

		for (int i=0;i<serviceRequest.imageUrl.length();i++){
			try {
				//images.add(Picasso.get().load(serviceRequest.imageUrl));
				Picasso.get().load(serviceRequest.imageUrl).into(images);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}*/
	/*	SlidingImageAdapter slidingimageAdapter = new SlidingImageAdapter(getApplicationContext(), images);
		viewPager.setAdapter(slidingimageAdapter);


		indicator_tab.setupWithViewPager(viewPager, true);*/
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
		if(serviceRequest.proposals.size()>0){
			btn_edit.setVisibility(View.VISIBLE);
			btn_edit.setText("Proposal");

			btn_edit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(DetailsActivity.this,ProposalActivity.class);
					intent.putExtra("serviceRequest",serviceRequest);
					startActivity(intent);
				}
			});

		}



	}


}

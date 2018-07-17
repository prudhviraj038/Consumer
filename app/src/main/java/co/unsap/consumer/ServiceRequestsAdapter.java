package co.unsap.consumer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

import co.unsap.consumer.datamodels.Service;
import co.unsap.consumer.datamodels.ServiceRequest;

public class ServiceRequestsAdapter extends BaseAdapter  {
    Context context;
    ArrayList<ServiceRequest> menuItems;
    String string;




    private static LayoutInflater inflater=null;

    public ServiceRequestsAdapter(Context mainActivity, ArrayList<ServiceRequest> menuItems) {
        // TODO Auto-generated constructor stubcontext=mainActivity;
        this.context = mainActivity;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.menuItems = menuItems;

    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return menuItems.size();

        //return 10;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    public class Holder
    {
        TextView problemtitle,problem,problemdate,status_text;
        ImageView problemimage;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        Holder holder=new Holder();

        View rowView;

        rowView = inflater.inflate(R.layout.servicerequest_item, null);


        holder.problemtitle=(TextView) rowView.findViewById(R.id.problem_title);
        holder.problemtitle.setText(menuItems.get(position).serviceName);

        holder.problemimage = (ImageView) rowView.findViewById(R.id.problem_image);


        if(menuItems.get(position).images.size()>0)
        Picasso.with(context).load(menuItems.get(position).images.get(0).image_url).into(holder.problemimage);


        holder.problem = (TextView)rowView.findViewById(R.id.problem);
        holder.problem.setText(menuItems.get(position).subServiceName);

        holder.problemdate = (TextView)rowView.findViewById(R.id.problem_date);
        holder.problemdate.setText(menuItems.get(position).scheduleOn);

        holder.status_text = (TextView)rowView.findViewById(R.id.age);
       string=menuItems.get(position).serviceType;
      //  ||||
       if (string.equals("N")){
          // Toast.makeText(context,"NEW",Toast.LENGTH_LONG).show();
           holder.status_text.setText("New");
       }
       else if (string.equals("A")){
           holder.status_text.setText("Assigned");
        }
        else if (string.equals("C"))
        {
            holder.status_text.setText("Confirmed");
        }
        else if (string.equals("F")){
           holder.status_text.setText("Completed");
       }
       else if (string.equals("D")){
            holder.status_text.setText("Closed");
       }






        return rowView;
        
    }




}
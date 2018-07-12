package co.unsap.consumer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

import co.unsap.consumer.datamodels.Service;
import co.unsap.consumer.datamodels.ServiceRequest;

public class ServiceRequestsAdapter extends BaseAdapter  {
    Context context;
    ArrayList<ServiceRequest> menuItems;




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
        TextView cur_symbol,cur_name;
        ImageView country_flag;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        Holder holder=new Holder();

        View rowView;

        rowView = inflater.inflate(R.layout.services_item, null);


        holder.cur_symbol=(TextView) rowView.findViewById(R.id.menu_iem);
        holder.cur_symbol.setText(menuItems.get(position).message);

        holder.country_flag = (ImageView) rowView.findViewById(R.id.menu_icon);

        if(menuItems.get(position).images.length()>0)
            try {


                Picasso.with(context).load(menuItems.get(position).images.getJSONObject(0).getString("secureFileUrl")).into(holder.country_flag);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        return rowView;
        
    }




}
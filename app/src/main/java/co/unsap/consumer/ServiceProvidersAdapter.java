package co.unsap.consumer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.unsap.consumer.datamodels.Service;
import co.unsap.consumer.datamodels.ServiceProvider;

public class ServiceProvidersAdapter extends BaseAdapter  {
    Context context;
    ArrayList<ServiceProvider> serviceProviders;




    private static LayoutInflater inflater=null;
    public ServiceProvidersAdapter(Context mainActivity, ArrayList<ServiceProvider> serviceProviders) {
        // TODO Auto-generated constructor stubcontext=mainActivity;
        this.context = mainActivity;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.serviceProviders = serviceProviders;

    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return serviceProviders.size();

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
        TextView tv_providerName,tv_providerservice,tv_providerAddress,tv_distance,tv_charge,tv_rating;
        ImageView iv_providerImage;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        Holder holder=new Holder();

        View rowView;

        rowView = inflater.inflate(R.layout.item_serviceprovider, null);


        holder.tv_providerName=(TextView) rowView.findViewById(R.id.providername);
        holder.tv_providerservice=(TextView) rowView.findViewById(R.id.providingservive_name);
        holder.tv_providerAddress=(TextView) rowView.findViewById(R.id.provider_address);
        holder.tv_distance=(TextView) rowView.findViewById(R.id.distance);
        holder.tv_charge=(TextView) rowView.findViewById(R.id.tv_charge);


        holder.tv_providerName.setText(serviceProviders.get(position).fullName);
        holder.tv_providerservice.setText(serviceProviders.get(position).serviceName);
        holder.tv_providerAddress.setText(serviceProviders.get(position).serviceAddress);
        holder.tv_distance.setText("1.5 mi" );
        holder.tv_charge.setText(serviceProviders.get(position).providercharge);

        holder.iv_providerImage = (ImageView) rowView.findViewById(R.id.provider_img);

        Picasso.with(context).load(serviceProviders.get(position).imageUrl).into(holder.iv_providerImage);


        return rowView;
        
    }




}
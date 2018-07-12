package co.unsap.consumer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import co.unsap.consumer.datamodels.SubService;

public class SubServicesAdapter extends BaseAdapter  {
    Context context;
    ArrayList<SubService> subServices;
    private static LayoutInflater inflater=null;
    public SubServicesAdapter(Context mainActivity, ArrayList<SubService> subServices) {
        // TODO Auto-generated constructor stubcontext=mainActivity;
        this.context = mainActivity;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.subServices = subServices;

    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return subServices.size();
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

        rowView = inflater.inflate(R.layout.sub_service_item, null);

        holder.cur_symbol=(TextView) rowView.findViewById(R.id.menu_iem);
        holder.cur_symbol.setText(subServices.get(position).title);


        return rowView;
        
    }




}
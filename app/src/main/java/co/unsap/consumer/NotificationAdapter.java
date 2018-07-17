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

import co.unsap.consumer.datamodels.Notifications;
import co.unsap.consumer.datamodels.ServiceRequest;

public class NotificationAdapter extends BaseAdapter  {
    Context context;
    ArrayList<Notifications> menuItems;
    String string;




    private static LayoutInflater inflater=null;

    public NotificationAdapter(Context mainActivity, ArrayList<Notifications> menuItems) {
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
        TextView noftifytext;

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        Holder holder=new Holder();

        View rowView;

        rowView = inflater.inflate(R.layout.notification_item, null);

        holder.noftifytext = (TextView)rowView.findViewById(R.id.notification_text);
        holder.noftifytext.setText(menuItems.get(position).notify_sender);




        return rowView;
        
    }




}
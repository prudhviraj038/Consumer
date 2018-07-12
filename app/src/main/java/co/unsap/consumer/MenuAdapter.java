package co.unsap.consumer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MenuAdapter extends BaseAdapter  {
    Context context;
    ArrayList<MenuItem> menuItems;




    private static LayoutInflater inflater=null;
    public MenuAdapter(Context mainActivity, ArrayList<MenuItem> menuItems) {
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

        rowView = inflater.inflate(R.layout.menu_item, null);




        holder.cur_symbol=(TextView) rowView.findViewById(R.id.menu_iem);
        holder.cur_symbol.setText(menuItems.get(position).title);



        holder.country_flag = (ImageView) rowView.findViewById(R.id.menu_icon);
        holder.country_flag.setImageResource(menuItems.get(position).icon);

        //Picasso.with(context).load(categories.get(position).icon).into(holder.country_flag);


        return rowView;
        
    }




}
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

import co.unsap.consumer.datamodels.Proposal;
import co.unsap.consumer.datamodels.SubService;

public class ProposalAdapter extends BaseAdapter  {
    Context context;
    ArrayList<Proposal> subServices;
    private static LayoutInflater inflater=null;
    public ProposalAdapter(Context mainActivity, ArrayList<Proposal> subServices) {
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
        TextView proposer_name,proposer_reviews,proposer_comment;
        ImageView proposer_image;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        Holder holder=new Holder();

        View rowView;

        rowView = inflater.inflate(R.layout.item_proposal, null);

        holder.proposer_name=(TextView) rowView.findViewById(R.id.tv_proposer_name);
        holder.proposer_name.setText(subServices.get(position).proposer_name);


        holder.proposer_reviews=(TextView) rowView.findViewById(R.id.tv_proposar_reviews);
        holder.proposer_reviews.setText(subServices.get(position).proposer_reviews + " Reviews");


        holder.proposer_comment=(TextView) rowView.findViewById(R.id.tv_proposer_comment);
        holder.proposer_comment.setText(subServices.get(position).latest_comment);


        holder.proposer_image=(ImageView) rowView.findViewById(R.id.iv_proposer);
        Picasso.with(context).load(subServices.get(position).proposer_profile_pic).into(holder.proposer_image);

        return rowView;
        
    }




}
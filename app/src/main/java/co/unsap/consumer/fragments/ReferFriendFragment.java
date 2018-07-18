package co.unsap.consumer.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import co.unsap.consumer.LoginActivity;
import co.unsap.consumer.ProgressInterface;
import co.unsap.consumer.R;
import co.unsap.consumer.SessionManager;

/**
 * Created by mac on 3/18/17.
 */

public class ReferFriendFragment extends Fragment {

    private ProgressInterface progressInterface;
    LinearLayout logout;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            progressInterface = (ProgressInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + e.getMessage());
        }
    }


    public static ReferFriendFragment newInstance(int someInt) {
        ReferFriendFragment myFragment = new ReferFriendFragment();

        Bundle args = new Bundle();
        args.putInt("someInt", someInt);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_referafriend, container, false);
        String shareBody = "Look at this awesome app for aspiring service Providers! Use this Reference Code : https://www.u-snap.co/";
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));


        return view;
    }

}

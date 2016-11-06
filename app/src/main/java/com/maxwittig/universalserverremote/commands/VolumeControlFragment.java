package com.maxwittig.universalserverremote.commands;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.maxwittig.universalserverremote.PostSender;
import com.maxwittig.universalserverremote.R;

public class VolumeControlFragment extends Fragment
{
    private Button volumeUpButton;
    private Button volumeDownButton;
    private PostSender postSender;

    public VolumeControlFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void setArguments(Bundle args)
    {
        postSender = args.getParcelable("PostSender");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_volume_control, container, false);
        volumeDownButton = (Button)view.findViewById(R.id.volumeDownButton);
        volumeDownButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                postSender.send("VolumeControlCommand", "-");
            }
        });
        volumeUpButton = (Button)view.findViewById(R.id.volumeUpButton);
        volumeUpButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                postSender.send("VolumeControlCommand", "+");
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

}

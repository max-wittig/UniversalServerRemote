package com.maxwittig.universalserverremote.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.maxwittig.universalserverremote.AbstractPostSendFragment;
import com.maxwittig.universalserverremote.R;

public class VolumeControlFragment extends AbstractPostSendFragment
{
    private Button volumeUpButton;
    private Button volumeDownButton;
    private TextView volumeResponseTextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_volume_control, container, false);
        volumeResponseTextView = (TextView)view.findViewById(R.id.volumeResponseTextView);
        volumeDownButton = (Button)view.findViewById(R.id.volumeDownButton);
        volumeDownButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                postSender.send("VolumeControlCommand", "-");
                volumeResponseTextView.setText(postSender.getResponse());
            }
        });
        volumeUpButton = (Button)view.findViewById(R.id.volumeUpButton);
        volumeUpButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                postSender.send("VolumeControlCommand", "+");
                volumeResponseTextView.setText(postSender.getResponse());
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

}

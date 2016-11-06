package com.maxwittig.universalserverremote.commands;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.maxwittig.universalserverremote.PostSender;
import com.maxwittig.universalserverremote.R;


public class AlertFragment extends Fragment
{
    private Button sendButton;
    private EditText alertEditText;
    private PostSender postSender;

    public AlertFragment()
    {

        // Required empty public constructor
    }

    @Override
    public void setArguments(Bundle bundle)
    {
        postSender = bundle.getParcelable("PostSender");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_alert, container, false);
        sendButton = (Button)view.findViewById(R.id.sendAlertButton);
        sendButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getActivity(), alertEditText.getText().toString(), Toast.LENGTH_SHORT).show();
                postSender.send("AlertCommand", alertEditText.getText().toString());

            }
        });
        alertEditText = (EditText)view.findViewById(R.id.alertEditText);
        // Inflate the layout for this fragment
        return view;
    }

}
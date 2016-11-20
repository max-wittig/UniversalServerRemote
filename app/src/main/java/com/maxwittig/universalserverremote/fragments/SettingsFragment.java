package com.maxwittig.universalserverremote.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.maxwittig.universalserverremote.AbstractPostSendFragment;
import com.maxwittig.universalserverremote.R;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends AbstractPostSendFragment
{
    private EditText ipEditText;
    private EditText portEditText;
    private Button settingsSaveButton;
    private final String PREFS_NAME = "main";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        // Inflate the layout for this fragment
        ipEditText = (EditText) view.findViewById(R.id.ipEditText);
        portEditText = (EditText) view.findViewById(R.id.portEditText);
        settingsSaveButton = (Button) view.findViewById(R.id.saveSettingsButton);
        settingsSaveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String portString = portEditText.getText().toString();
                String ipString = ipEditText.getText().toString();
                postSender.getSettings().setPort(Integer.parseInt(portString));
                postSender.getSettings().setUrl(ipString);
                hideSoftKeyBoard();
                saveSharedPreferences(ipString, Integer.parseInt(portString));
            }
        });
        //portEditText.setText(String.valueOf(postSender.getSettings().getPort()));
        //ipEditText.setText(postSender.getSettings().getUrl());
        loadSharedPreferences();
        return view;
    }

    private void loadSharedPreferences()
    {
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        if(settings != null)
        {
            ipEditText.setText(settings.getString("hostIP", "127.0.0.1"));
            portEditText.setText(String.valueOf(settings.getInt("hostPort", 8000)));
        }
    }

    private void saveSharedPreferences(String ip, int port)
    {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("hostIP", ip);
        editor.putInt("hostPort", port);
        editor.apply();
    }

    private void hideSoftKeyBoard()
    {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);

        if (imm.isAcceptingText())
        { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

}

package com.maxwittig.universalserverremote;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment
{
    private PostSender postSender;
    private EditText ipEditText;
    private EditText portEditText;
    private Button settingsSaveButton;

    public SettingsFragment()
    {

        // Required empty public constructor
    }

    @Override
    public void setArguments(Bundle args)
    {
        postSender = args.getParcelable("PostSender");
        super.setArguments(args);
    }

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
                postSender.getSettings().setPort(Integer.parseInt(portEditText.getText().toString()));
                postSender.getSettings().setUrl(ipEditText.getText().toString());
                hideSoftKeyBoard();
            }
        });
        portEditText.setText(String.valueOf(postSender.getSettings().getPort()));
        ipEditText.setText(postSender.getSettings().getUrl());
        return view;
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

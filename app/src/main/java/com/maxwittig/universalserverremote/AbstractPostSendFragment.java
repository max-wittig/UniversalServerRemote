package com.maxwittig.universalserverremote;


import android.app.Fragment;
import android.os.Bundle;

public abstract class AbstractPostSendFragment extends Fragment
{

    protected PostSender postSender;

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        outState.putParcelable("PostSender", postSender);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void setArguments(Bundle args)
    {
        postSender = args.getParcelable("PostSender");
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        if(savedInstanceState != null)
            postSender = savedInstanceState.getParcelable("PostSender");
        super.onCreate(savedInstanceState);
    }

}

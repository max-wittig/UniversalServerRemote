package com.maxwittig.universalserverremote;


import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.StrictMode;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


//Thanks to http://www.parcelabler.com/
public class PostSender implements Parcelable {
    private Context context;
    private Settings settings;

    public PostSender(Context context, Settings settings)
    {
        this.context = context;
        this.settings = settings;
    }

    public void send(String key, String value)
    {
        try
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://"+settings.getUrl()+":"+settings.getPort()+"/");

            try
            {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair(key, value));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);

            }
            catch (ClientProtocolException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();

                // TODO Auto-generated catch block
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    protected PostSender(Parcel in) {
        context = (Context) in.readValue(Context.class.getClassLoader());
        settings = (Settings) in.readValue(Settings.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(context);
        dest.writeValue(settings);
    }

    public Settings getSettings()
    {
        return settings;
    }

    public void setSettings(Settings settings)
    {
        this.settings = settings;
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PostSender> CREATOR = new Parcelable.Creator<PostSender>() {
        @Override
        public PostSender createFromParcel(Parcel in) {
            return new PostSender(in);
        }

        @Override
        public PostSender[] newArray(int size) {
            return new PostSender[size];
        }
    };
}
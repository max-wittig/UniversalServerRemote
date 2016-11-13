package com.maxwittig.universalserverremote;


import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.StrictMode;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


//Thanks to http://www.parcelabler.com/
public class PostSender implements Parcelable {
    private Context context;
    private Settings settings;
    private String response = "";

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
                this.response = getContentsFromInputStream(response.getEntity().getContent());

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

    public String getResponse()
    {
        return response;
    }

    private static String getContentsFromInputStream(InputStream inputStream) throws Exception
    {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
        String line = "";
        StringBuilder text = new StringBuilder();

        while (line != null)
        {
            line = bufferedReader.readLine();
            if (line != null)
                text.append(line);
        }
        return text.toString();
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
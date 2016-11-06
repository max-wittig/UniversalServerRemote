package com.maxwittig.universalserverremote;

import android.os.Parcel;
import android.os.Parcelable;

public class Settings implements Parcelable
{
    private String url = "127.0.0.1";
    private int port = 8000;

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public Settings()
    {
    }

    protected Settings(Parcel in)
    {
        url = in.readString();
        port = in.readInt();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(url);
        dest.writeInt(port);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Settings> CREATOR = new Parcelable.Creator<Settings>()
    {
        @Override
        public Settings createFromParcel(Parcel in)
        {
            return new Settings(in);
        }

        @Override
        public Settings[] newArray(int size)
        {
            return new Settings[size];
        }
    };
}
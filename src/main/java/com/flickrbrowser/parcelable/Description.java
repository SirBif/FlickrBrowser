package com.flickrbrowser.parcelable;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * Created by bif on 15/04/14.
 */
public class Description implements Parcelable {
    @SerializedName("_content")
    protected String description;

    public Description(String content) {
        description = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(description);
    }

    public Description(Parcel in) {
        description = in.readString();
    }

    public static final Parcelable.Creator<Description> CREATOR= new Parcelable.Creator<Description>() {
        @Override
        public Description createFromParcel(Parcel source) {
            return new Description(source);  //using parcelable constructor
        }

        @Override
        public Description[] newArray(int size) {
            return new Description[size];
        }
    };

}

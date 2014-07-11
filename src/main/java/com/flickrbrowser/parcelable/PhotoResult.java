package com.flickrbrowser.parcelable;

import android.os.Parcel;
import android.os.Parcelable;
import com.flickrbrowser.util.PhotoSize;

/**
 An object representing a single Flickr photo. Contains the photo details and methods to retrieve the image url
 */
public class PhotoResult implements Parcelable{
    private String id;
    private String owner;
    private String secret;
    private String farm;
    private String server;
    private String title;
    private Description description;

    public PhotoResult() {

    }

    public PhotoResult(Parcel in) {
        id = in.readString();
        owner = in.readString();
        secret =in.readString();
        farm = in.readString();
        server = in.readString();
        title = in.readString();
        description = (Description) in.readValue(Description.class.getClassLoader());
    }


    public String getUrl(PhotoSize size) {
        return "http://farm" + farm + ".staticflickr.com/" + server + "/" + id + "_" + secret + "_" + size + ".jpg";
    }

    public String getWebPageUrl() {
        return "http://www.flickr.com/photos/" +owner +"/"+ id;
    }

    public String getDescriptionString() {
        return this.description != null ? this.description.toString() : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(id);
        parcel.writeString(owner);
        parcel.writeString(secret);
        parcel.writeString(farm);
        parcel.writeString(server);
        parcel.writeString(title);
        parcel.writeValue(description);
    }

    public static final Parcelable.Creator<PhotoResult> CREATOR= new Parcelable.Creator<PhotoResult>() {
        @Override
        public PhotoResult createFromParcel(Parcel source) {
            return new PhotoResult(source);  //using parcelable constructor
        }

        @Override
        public PhotoResult[] newArray(int size) {
            return new PhotoResult[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getFarm() {
        return farm;
    }

    public void setFarm(String farm) {
        this.farm = farm;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getOwner() {
        return owner;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }
}
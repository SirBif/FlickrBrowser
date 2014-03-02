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
    private String description;

    public PhotoResult() {

    }

    public PhotoResult(Parcel in) {
        String[] data= new String[7];
        in.readStringArray(data);
        id = data[0];
        owner = data[1];
        secret = data[2];
        farm = data[3];
        server = data[4];
        title = data[5];
        description = data[6];
    }


    public String getUrl(PhotoSize size) {
        return "http://farm" + farm + ".staticflickr.com/" + server + "/" + id + "_" + secret + "_" + size + ".jpg";
    }

    public String getWebPageUrl() {
        return "http://www.flickr.com/photos/" +owner +"/"+ id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                this.id,
                this.owner,
                this.secret,
                this.farm,
                this.server,
                this.title,
                this.description
        });
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getOwner() {
        return owner;
    }

}
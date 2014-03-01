package com.flickrbrowser.rest;

import android.os.Parcel;
import android.os.Parcelable;
import com.flickrbrowser.util.FlickrBrowserConstants;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/27/14
 * Time: 11:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class PhotoResult implements Parcelable{
    private String id;
    private String owner;
    private String secret;
    private String farm;
    private String server;
    private String title;
    private String description;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getOwner() {
        return owner;
    }

    public PhotoResult(Element element) {
        id = element.getAttribute(FlickrBrowserConstants.XmlAttributes.ID);
        owner = element.getAttribute(FlickrBrowserConstants.XmlAttributes.OWNER);
        secret = element.getAttribute(FlickrBrowserConstants.XmlAttributes.SECRET);
        farm = element.getAttribute(FlickrBrowserConstants.XmlAttributes.FARM);
        server = element.getAttribute(FlickrBrowserConstants.XmlAttributes.SERVER);
        title = element.getAttribute(FlickrBrowserConstants.XmlAttributes.TITLE);

        NodeList descriptions = element.getElementsByTagName(FlickrBrowserConstants.XmlAttributes.DESCRIPTION_ATTRIBUTE_NAME);
        if(descriptions.getLength() > 0) {
            Node desc = descriptions.item(0);
            if(desc.hasChildNodes()) {
                description = descriptions.item(0).getFirstChild().getNodeValue();
            }
        }
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

}
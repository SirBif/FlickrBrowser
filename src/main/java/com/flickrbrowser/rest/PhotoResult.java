package com.flickrbrowser.rest;

import com.flickrbrowser.util.FlickrBrowserConstants;
import org.w3c.dom.Element;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/27/14
 * Time: 11:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class PhotoResult {
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
        description = element.getAttribute(FlickrBrowserConstants.XmlAttributes.DESCRIPTION);
    }

    public String getUrl(PhotoSize size) {
        return "http://farm" + farm + ".staticflickr.com/" + server + "/" + id + "_" + secret + "_" + size + ".jpg";
    }
}

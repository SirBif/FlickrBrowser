package com.flickrbrowser.rest;

import com.flickrbrowser.util.FlickrPhotoArray;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 3/1/14
 * Time: 1:48 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IRequestListener {
    public void notifyEnd(FlickrPhotoArray response);
}

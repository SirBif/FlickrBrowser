package com.flickrbrowser.util;

import com.flickrbrowser.parcelable.PhotoResult;

/**
 * Created by bif on 14/04/14.
 */
public class FlickrPhotoArray {
    private int page;
    private int pages;
    private int perpage;
    private int total;
    private PhotoResult[] photo;


    public int getPage() {
        return page;
    }

    public int getPages() {
        return pages;
    }

    public int getPerpage() {
        return perpage;
    }

    public int getTotal() {
        return total;
    }

    public PhotoResult[] getPhoto() {
        return photo;
    }
}

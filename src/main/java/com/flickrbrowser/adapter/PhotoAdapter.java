package com.flickrbrowser.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.flickrbrowser.parcelable.PhotoResult;

import java.util.List;

/**
 Generic image adapter that handles instances of PhotoResult but does not display anything
@see PhotoResult
 */
public class PhotoAdapter extends BaseAdapter {
    protected  List<PhotoResult> photos;

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object getItem(int position) {
        return photos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    public List<PhotoResult> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoResult> photos) {
        this.photos = photos;
    }
}

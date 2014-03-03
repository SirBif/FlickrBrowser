package com.flickrbrowser.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.flickrbrowser.parcelable.PhotoResult;
import com.flickrbrowser.parcelable.SearchResult;

import java.util.ArrayList;
import java.util.List;

/**
 Generic image adapter that handles instances of PhotoResult but does not display anything
@see PhotoResult
 */
public class PhotoAdapter extends BaseAdapter {
    protected  List<PhotoResult> photos = new ArrayList<PhotoResult>();
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

    public void addPhotos(List<PhotoResult> itemsToAdd) {
        photos.addAll(itemsToAdd);
        notifyDataSetChanged();
    }

    public void clearPhotos() {
        photos.clear();
        notifyDataSetChanged();
    }
}

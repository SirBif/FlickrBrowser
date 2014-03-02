package com.flickrbrowser.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.flickrbrowser.rest.PhotoResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 3/2/14
 * Time: 2:59 AM
 * To change this template use File | Settings | File Templates.
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

    public void addPhotoResults(List<PhotoResult> itemsToAdd) {
        photos.addAll(itemsToAdd);
        notifyDataSetChanged();
    }

    public void clearPhotos() {
        photos.clear();
        notifyDataSetChanged();
    }
}

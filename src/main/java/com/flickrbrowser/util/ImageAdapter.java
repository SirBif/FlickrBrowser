package com.flickrbrowser.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.flickrbrowser.rest.PhotoResult;
import com.flickrbrowser.rest.PhotoSize;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/25/14
 * Time: 10:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<PhotoResult> photos = new ArrayList<PhotoResult>();
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return photos.size();
    }

    public Object getItem(int position) {
        return photos.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public void addPhotoResults(List<PhotoResult> itemsToAdd) {
        photos.addAll(itemsToAdd);
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        imageLoader.displayImage(photos.get(position).getUrl(PhotoSize.THUMBNAIL), imageView);
        return imageView;
    }
}

package com.flickrbrowser.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.flickrbrowser.R;
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
    private static LayoutInflater inflater=null;

    public ImageAdapter(Context c) {
        mContext = c;
        inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        notifyDataSetChanged();
    }

    public void clearPhotos() {
        photos.clear();
        notifyDataSetChanged();
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout vi;
        vi = (RelativeLayout) inflater.inflate(R.layout.listrow, null);

        TextView title = (TextView)vi.findViewById(R.id.display_title); // title
        TextView desc = (TextView)vi.findViewById(R.id.display_description); // artist name
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image

        PhotoResult photo = photos.get(position);

        // Setting all values in listview
        title.setText(shortenString(photo.getTitle()));
        desc.setText(shortenString(photo.getDescription()));
        imageLoader.displayImage(photo.getUrl(PhotoSize.THUMBNAIL), thumb_image);
        return vi;
    }

    private static String shortenString(String input) {
        if(input != null && input.length() > 100) {
            return input.substring(0, 100) + "...";
        }
        return input;
    }
}

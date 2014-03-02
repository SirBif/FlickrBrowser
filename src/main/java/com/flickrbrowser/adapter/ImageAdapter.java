package com.flickrbrowser.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.flickrbrowser.R;
import com.flickrbrowser.parcelable.PhotoResult;
import com.flickrbrowser.util.PhotoSize;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/25/14
 * Time: 10:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageAdapter extends PhotoAdapter {
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private static LayoutInflater inflater=null;

    public ImageAdapter(Context c) {
        inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout vi;
        vi = (RelativeLayout) inflater.inflate(R.layout.listrow, null);

        TextView title = (TextView)vi.findViewById(R.id.display_title);
        TextView desc = (TextView)vi.findViewById(R.id.display_description);
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image);

        PhotoResult photo = photos.get(position);

        title.setText(shortenString(photo.getTitle()));
        desc.setText(shortenString(photo.getDescription()));
        imageLoader.displayImage(photo.getUrl(PhotoSize.SMALL_SQUARE), thumb_image);
        return vi;
    }

    private static String shortenString(String input) {
        if(input != null && input.length() > 100) {
            return input.substring(0, 100) + "...";
        }
        return input;
    }
}

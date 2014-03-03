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
import com.flickrbrowser.util.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
Extension of PhotoAdapter that takes care of displaying the results
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

        title.setText(Utils.niceString(photo.getTitle()));
        desc.setText(Utils.niceString(photo.getDescription()));
        imageLoader.displayImage(photo.getUrl(PhotoSize.SMALL_SQUARE), thumb_image);
        return vi;
    }


}

package com.flickrbrowser.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.flickrbrowser.R;
import com.flickrbrowser.parcelable.PhotoResult;
import com.flickrbrowser.util.FlickrBrowserConstants;
import com.flickrbrowser.util.PhotoSize;
import com.flickrbrowser.util.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 Activity used to display single photo results
 */
public class Display extends Activity {
    private ImageLoader imageLoader;
    private TextView title;
    private TextView desc;
    private ImageView image;
    private Intent shareIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        imageLoader = ImageLoader.getInstance();
        image=(ImageView)this.findViewById(R.id.display_image);
        title=(TextView)this.findViewById(R.id.display_title);
        desc=(TextView)this.findViewById(R.id.display_description);
        handleIntent(getIntent());
    }

    @Override
    public void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    protected void handleIntent(Intent intent) {
        PhotoResult photo = intent.getParcelableExtra(FlickrBrowserConstants.ParcelablePhoto);
        imageLoader.displayImage(photo.getUrl(PhotoSize.MEDIUM_640), image);
        title.setText(Utils.niceString(photo.getTitle()));
        desc.setText(Utils.niceString(photo.getDescription()));

        shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, photo.getWebPageUrl());
        shareIntent.setType("text/plain");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.display_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.menu_item_share:
                setShareIntent(shareIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.share_photo)));
    }
}

package com.flickrbrowser.activity;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.flickrbrowser.R;
import com.flickrbrowser.util.ImageAdapter;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.HttpParams;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ListView listview = (ListView) findViewById(android.R.id.list);
        listview.setAdapter(new ImageAdapter(this));

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

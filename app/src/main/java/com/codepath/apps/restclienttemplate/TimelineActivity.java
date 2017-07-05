package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.fragments.TweetsListFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsPagerAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.codepath.apps.restclienttemplate.R.id.swipeContainer;
import static com.codepath.apps.restclienttemplate.models.SampleModel_Table.name;

public class TimelineActivity extends AppCompatActivity {

    // TweetsListFragment fragmentTweetsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);


       // fragmentTweetsList = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_timeline);

        // get the view pager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);

        // set the adapter for the pager
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager(), this));

        // setup the TabLayout to use the view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);

    }

//    public void fetchTimelineAsync(int page) {
//        // Send the network request to fetch the updated data
//        // `client` here is an instance of Android Async HTTP
//        // getHomeTimeline is an example endpoint.
//
//        client.getHomeTimeline(new JsonHttpResponseHandler() {
//            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
//
//                // Remember to CLEAR OUT old items before appending in the new ones
//                tweetAdapter.clear();
//
//                // ...the data has come back, add new items to your adapter...
//                tweetAdapter.addAll(Tweet.fromJSONArray(json));
//
//                // Now we call setRefreshing(false) to signal refresh has finished
//                swipeContainer.setRefreshing(false);
//            }
//
//            public void onFailure(Throwable e) {
//                Log.d("DEBUG", "Fetch timeline error: " + e.toString());
//            }
//        });
//    }

    // method to add button to the action bar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void onComposeAction(MenuItem mi) {
        // handle compose button click here
        Intent myIntent = new Intent(this, ComposeActivity.class);
        startActivityForResult(myIntent, 20);
    }

    // click listener method for profile image
    public void onProfileView(MenuItem item)
    {
        // launch the profile view
        Intent i = new Intent(this, ProfileActivity.class);

        // now pass in the screenName! If you don't it defaults to your own
        startActivity(i);
    }

}

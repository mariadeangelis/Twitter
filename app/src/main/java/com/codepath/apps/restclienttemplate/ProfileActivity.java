package com.codepath.apps.restclienttemplate;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.codepath.apps.restclienttemplate.fragments.UserTimelineFragment;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {

    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String screenName = getIntent().getStringExtra("screen_name");
        // create the user fragment
        UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);

        // display the user timeline inside the container (dynamically)
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        // make change
        ft.replace(R.id.flContainer, userTimelineFragment);

        // commit transaction
        ft.commit();

        client = TwitterApp.getRestClient();
        client.getUserInfo(new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {

                    // deserialize the user object
                    User user = User.fromJSON(response);

                    // set the title of the ActionBar based on the user information
                    getSupportActionBar().setTitle(user.screenName);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        });
    }

}

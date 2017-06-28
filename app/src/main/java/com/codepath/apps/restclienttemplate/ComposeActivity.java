package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import android.support.design.widget.TextInputLayout;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    // tweet to be passed to the other activity
    public Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
    }

    // Called when the button has been clicked
    public void tweetDone(View view)
    {
        // Get a reference with the edit text
        EditText et = (EditText) findViewById(R.id.et_simple);

        // Create Twitter Client and send
        TwitterClient client = TwitterApp.getRestClient();
        client.sendTweet(et.getText().toString(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //super.onSuccess(statusCode, headers, response);

                // Do the whole try catch thing and get the tweet itself
                try {

                    tweet = Tweet.fromJSON(response);

                    // Prepare data intent
                    //Intent data = new Intent(ComposeActivity.this, TimelineActivity.class);
                    Intent data = new Intent();

                    // Pass relevant data back as a result
                    data.putExtra("new_tweet", Parcels.wrap(tweet));

                    // Activity finished ok, return the data
                    setResult(RESULT_OK, data); // set result code and bundle data for response
                    finish(); // closes the activity, pass data to parent
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }

            // Stupid Failure error handlers
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("TwitterClient", responseString);
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }
        });
    }
}

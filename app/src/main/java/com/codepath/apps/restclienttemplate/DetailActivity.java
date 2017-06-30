package com.codepath.apps.restclienttemplate;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

import static com.codepath.apps.restclienttemplate.R.id.tvBody;

public class DetailActivity extends AppCompatActivity {

    Tweet tweet;
    String name;
    TextView tvBody;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra("curr_tweet"));
        name = tweet.getUser().getName();
        String body = tweet.getBody();
        id = tweet.getUid();

        tvBody = (TextView) findViewById(R.id.tvBody);
        tvBody.setText(body);

        TextView tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(name);

        // gets called when favorite button is clicked
        ImageButton clickButton = (ImageButton) findViewById(R.id.favorite_button);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Create Twitter Client and send
                TwitterClient client = TwitterApp.getRestClient();
                client.favTweet(id, new JsonHttpResponseHandler(){
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
                            //data.putExtra("new_tweet", Parcels.wrap(tweet));

                            // Activity finished ok, return the data
                            //setResult(RESULT_OK, data); // set result code and bundle data for response
                            //finish(); // closes the activity, pass data to parent
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
        });
    }

}

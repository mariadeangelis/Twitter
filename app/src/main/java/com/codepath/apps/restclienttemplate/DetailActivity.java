package com.codepath.apps.restclienttemplate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Tweet tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra("curr_tweet"));
        String name = tweet.getUser().getName();
        String body = tweet.getBody();

        TextView tvBody = (TextView) findViewById(R.id.tvBody);
        tvBody.setText(body);

        TextView tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(name);
    }
}

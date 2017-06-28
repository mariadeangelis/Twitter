package com.codepath.apps.restclienttemplate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * Created by mariadeangelis on 6/26/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>
{

    // Needed to make this global to the class for the reply button listener to recognize it
    Tweet tweet;

    private List<Tweet> mtweets;
    Context context;
    // pass in the Tweets array in the constructor
    public TweetAdapter(List<Tweet> tweets){
        mtweets = tweets;
    }

    // for each row, inflate the layout and cache references into ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        context = parent.getContext();
        LayoutInflater inflator = LayoutInflater.from(context);
        View tweetView = inflator.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    // bind the values based on the position of the element
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get the data according to position
        tweet = mtweets.get(position);

        // populate the views according to this data
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);

        holder.tvTime.setText(getRelativeTimeAgo(tweet.createdAt));   // added to get tweet time to work

        Glide.with(context).load(tweet.user.profileImageUrl).into(holder.ivProfileImage);

        // Add on click listener
        Button clickButton = (Button) holder.reply_button;
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Package and send over the parent tweet
                Intent myIntent = new Intent(context, ComposeActivity.class);

                // Pass relevant data back as a result
                myIntent.putExtra("parent_tweet", Parcels.wrap(tweet));
                ((Activity)context).startActivityForResult(myIntent, 20); // same code as the other one
            }
        });
    }

    @Override
    public int getItemCount() {
        return mtweets.size();
    }

    // create the ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvTime;
        public Button reply_button;

        public ViewHolder(View itemView) {
            super(itemView);

            // perform findbyID lookups to connect to things we made in the layout view

            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            reply_button = (Button) itemView.findViewById(R.id.reply_button);
        }
    }

    // method to convert created_at to time created ago

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    // Methods for scroll up to refresh

    // Clean all elements of the recycler
    public void clear() {
        mtweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        mtweets.addAll(list);
        notifyDataSetChanged();
    }
}

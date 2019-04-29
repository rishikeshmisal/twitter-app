package com.twitter.twitterapp.utils;

import com.twitter.twitterapp.entity.Followers;
import com.twitter.twitterapp.entity.Tweets;
import com.twitter.twitterapp.entity.User;

import java.util.Date;
import java.util.Optional;

public class TestUtils {

    public static String USERNAME = "TEST";
    public static String PASSWORD = "PASS";
    public static String USERNAME1 = "TEST1";
    public static String PASSWORD1 = "PASS1";
    public static String TWEET = "Something to tweet! Something that I like.";
    public static String TWEET_EXTRA ="He looked the embodiment of India's famous opener, Gavaskar, " +
            "and indeed was wearing a pair of his pads. While he displayed a full repertoire of strokes in compiling his maiden Test " +
            "hundred, most remarkable were his off-side shots from the back foot. Though only 5ft 5in tall," +
            " he was still able to control without difficulty short deliveries from the English pacemen."+
            "Tendulkar's performance through the years 1994–1999 coincided with his physical peak," +
            " in his early twenties. He opened the batting at Auckland against New Zealand in 1994, making 82 runs off 49 balls"+
            "He scored his first ODI century on 9 September 1994 against Australia in Sri Lanka at Colombo.";

    public static String TWEET_ID_PASS = "134314";
    public static String TWEET_ID_FAIL = "abcd";
    public static User getUserObject(){

        User user = new User();
        user.setPassword(PASSWORD);
        user.setUsername(USERNAME);
        user.setId(1l);
        return user;
    }
    public static User getUserObject1(){

        User user = new User();
        user.setPassword(PASSWORD1);
        user.setUsername(USERNAME1);
        user.setId(2l);
        return user;
    }

    public static Optional<Followers> getFollowersObject(){
        Followers followers = new Followers();
        followers.setFollowTo(getUserObject1());
        followers.setFollowBy(getUserObject());
        followers.setId(1l);
        return Optional.of(followers);
    }

    public static Optional<Tweets> getTweetsObject(){
        Tweets tweets = new Tweets();
        tweets.setUser(getUserObject());
        tweets.setTweet(TWEET);
        tweets.setCreatedOn(new Date());
        tweets.setId(Long.parseLong(TWEET_ID_PASS));
        return Optional.of(tweets);
    }

    public static Optional<Followers> getFollowersEmpty(){
        return Optional.empty();
    }

    public static Optional<Tweets> getTweetEmpty(){
        return Optional.empty();
    }

}

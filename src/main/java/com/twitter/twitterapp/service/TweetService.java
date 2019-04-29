package com.twitter.twitterapp.service;

import com.twitter.twitterapp.dto.TweetDTO;
import com.twitter.twitterapp.entity.TweetLikes;
import com.twitter.twitterapp.entity.Tweets;
import com.twitter.twitterapp.entity.User;
import com.twitter.twitterapp.exception.TwitterApplicationException;
import com.twitter.twitterapp.helper.Constants;
import com.twitter.twitterapp.repository.ITweetLikes;
import com.twitter.twitterapp.repository.ITweetsRepo;
import com.twitter.twitterapp.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class TweetService {

    @Autowired
    private IUserRepo iUserRepo;

    @Autowired
    private ITweetsRepo iTweetsRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private ITweetLikes iTweetLikes;

    /**
     * Saves the tweetStr in tweets table against the user
     * with username
     *
     * @param username
     * @param tweetStr tweet string
     *
     */
    public void saveTweet(String username, String tweetStr) throws TwitterApplicationException {

        User user = userService.getUser(username);

        Tweets tweet = new Tweets();

        if(tweetStr.length()>Constants.CHAR_COUNT){
            throw new TwitterApplicationException("Character limit exceeded by "+(tweetStr.length()-Constants.CHAR_COUNT), HttpStatus.BAD_REQUEST);
        }

        tweet.setTweet(tweetStr);
        tweet.setUser(user);

        try {
            tweet = iTweetsRepo.save(tweet);
        }catch (Exception e){
            throw new TwitterApplicationException(Constants.SOMETHING_WENT_WRONG+" "+Constants.RESEND_TWEET_MSG);
        }

    }

    /**
     * deletes the tweet in tweets table against the user
     * with username
     *
     * @param username
     * @param tweetId
     *
     */

    public void deleteTweet(String username, String tweetId) throws TwitterApplicationException {

        User user = userService.getUser(username);

        long tweetIDLong = extractId(tweetId);

        Optional<Tweets> tweets = iTweetsRepo.findById(tweetIDLong);

        if(!tweets.isPresent())
            throw new TwitterApplicationException("Tweet does not exist",HttpStatus.BAD_REQUEST);

        if(tweets.get().getUser().getId()!=user.getId()){
            throw new TwitterApplicationException("You are not the author of the tweet",HttpStatus.BAD_REQUEST);
        }

        iTweetLikes.deleteRecordsOfLikesForTweet(tweets.get().getId());

        iTweetsRepo.delete(tweets.get());
    }

    /**
     * GETs the tweet timeline for the user
     * with username with desc order of creation
     *
     * @param username
     *
     */

    public List<TweetDTO> getTweets(String username) throws TwitterApplicationException {

        User user = userService.getUser(username);

        List<Tweets> tweets = iTweetsRepo.findByUserOrderByCreatedOnDesc(user);

        List<TweetDTO> response = new ArrayList<>();

        if(tweets!=null && !tweets.isEmpty()){
            tweets.forEach(t->{
                TweetDTO tweet = new TweetDTO();
                tweet.setUserId(t.getUser().getId());
                tweet.setTweet(t.getTweet());
                tweet.setCreatedOn(t.getCreatedOn());
                tweet.setTweetId(t.getId());
                tweet.setLikes(iTweetLikes.countByTweet(t));
                response.add(tweet);
            });
        }

        return response;

    }

    /**
     * Creates an entry in tweet_likes indicating
     * the user like for the tweet with tweetId
     *
     * @param username
     * @param tweetId tweet string
     *
     */

    public void likeTweet(String username, String tweetId) throws TwitterApplicationException {

        User user = userService.getUser(username);

        long tweetIDLong = extractId(tweetId);

        Optional<Tweets> tweets = iTweetsRepo.findById(tweetIDLong);

        if(!tweets.isPresent()){
            throw new TwitterApplicationException("Tweet does not exist",HttpStatus.BAD_REQUEST);
        }

        Optional<TweetLikes> present = iTweetLikes.findByUserAndTweet(user,tweets.get());

        if(present.isPresent()){
            throw new TwitterApplicationException("You have already liked the tweet",HttpStatus.BAD_REQUEST);
        }

        TweetLikes tweetLikes = new TweetLikes();
        tweetLikes.setTweet(tweets.get());
        tweetLikes.setUser(user);

        iTweetLikes.save(tweetLikes);

    }

    /**
     * Deletes the entry in tweet_likes indicating
     * the user's un-like for the tweet with tweetId
     *
     * @param username
     * @param tweetId tweet string
     *
     */

    public void unlikeTweet(String username, String tweetId) throws TwitterApplicationException {

        User user = userService.getUser(username);

        long tweetIDLong = extractId(tweetId);

        Optional<Tweets> tweets = iTweetsRepo.findById(tweetIDLong);

        if(!tweets.isPresent()){
            throw new TwitterApplicationException("Tweet does not exist",HttpStatus.BAD_REQUEST);
        }

        Optional<TweetLikes> present = iTweetLikes.findByUserAndTweet(user,tweets.get());

        if(!present.isPresent()){
            throw new TwitterApplicationException("You have not liked the tweet",HttpStatus.BAD_REQUEST);
        }

        iTweetLikes.delete(present.get());

    }

    private long extractId(String tweetId) throws TwitterApplicationException {

        long tweetIDLong;

        try{
            tweetIDLong = Long.parseLong(tweetId);
        }catch (Exception e){
            throw new TwitterApplicationException("Not a valid tweet id");
        }
        return tweetIDLong;
    }

}

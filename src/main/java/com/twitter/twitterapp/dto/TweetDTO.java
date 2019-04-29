package com.twitter.twitterapp.dto;

import java.util.Date;

public class TweetDTO {

    public Long getTweetId() {
        return tweetId;
    }

    public void setTweetId(Long tweetId) {
        this.tweetId = tweetId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getReTweetsCount() {
        return reTweetsCount;
    }

    public void setReTweetsCount(Integer reTweetsCount) {
        this.reTweetsCount = reTweetsCount;
    }

    public Integer getRepliesCount() {
        return repliesCount;
    }

    public void setRepliesCount(Integer repliesCount) {
        this.repliesCount = repliesCount;
    }

    private Long tweetId;

    private Long userId;

    private String tweet;

    private Date createdOn;

    private Integer likes=0;

    private  Integer reTweetsCount=0;

    private  Integer repliesCount=0;
}

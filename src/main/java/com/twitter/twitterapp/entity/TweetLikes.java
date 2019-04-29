package com.twitter.twitterapp.entity;

import javax.persistence.*;

@Entity
@Table(name = "tweet_likes")
public class TweetLikes {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tweets getTweet() {
        return tweet;
    }

    public void setTweet(Tweets tweet) {
        this.tweet = tweet;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    @OneToOne
    @JoinColumn(name="tweet_id", referencedColumnName = "id")
    private Tweets tweet;

}

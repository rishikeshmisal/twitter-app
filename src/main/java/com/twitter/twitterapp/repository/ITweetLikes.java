package com.twitter.twitterapp.repository;

import com.twitter.twitterapp.entity.TweetLikes;
import com.twitter.twitterapp.entity.Tweets;
import com.twitter.twitterapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITweetLikes extends JpaRepository<TweetLikes,Long> {

    Optional<TweetLikes> findByUserAndTweet(User user, Tweets tweets);

    List<TweetLikes> findByUser(User user);

    List<TweetLikes> findByTweet(Tweets tweets);

    Integer countByUserAndTweet(User user, Tweets tweets);

    Integer countByTweet(Tweets tweets);

    @Modifying
    @Transactional
    @Query(value = "delete from Tweet_Likes tl where tl.Tweet_Id = ?1",nativeQuery = true)
    void deleteRecordsOfLikesForTweet(Long tweetId);
}

package com.twitter.twitterapp.repository;

import com.twitter.twitterapp.entity.Tweets;
import com.twitter.twitterapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITweetsRepo extends JpaRepository<Tweets,Long> {

    Tweets findByIdAndUser(Long id,User user);

    List<Tweets> findByUserOrderByCreatedOnDesc(User user);
}


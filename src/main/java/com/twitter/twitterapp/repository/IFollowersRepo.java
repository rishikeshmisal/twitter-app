package com.twitter.twitterapp.repository;

import com.twitter.twitterapp.entity.Followers;
import com.twitter.twitterapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFollowersRepo extends JpaRepository<Followers,Long> {

    Optional<Followers> findByFollowByAndFollowTo(User followBy, User followTo);
}

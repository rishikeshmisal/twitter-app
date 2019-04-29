package com.twitter.twitterapp.repository;

import com.twitter.twitterapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepo extends JpaRepository<User,Long> {

    User findByUsername(String userName);
}

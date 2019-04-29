package com.twitter.twitterapp.service;

import com.twitter.twitterapp.entity.Followers;
import com.twitter.twitterapp.entity.User;
import com.twitter.twitterapp.exception.TwitterApplicationException;
import com.twitter.twitterapp.exception.UnauthorizedException;
import com.twitter.twitterapp.helper.Constants;
import com.twitter.twitterapp.repository.IFollowersRepo;
import com.twitter.twitterapp.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private IUserRepo iUserRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IFollowersRepo iFollowersRepo;

    /**
     * Registers a new user with a unique username and password,
     * requires a client_credentials token
     *
     *
     * @param clientId
     * @param userName a unique username for the user to be
     * identified
     * @param password
     */

    public void registerUser(String clientId,String userName, String password) throws TwitterApplicationException {

        if(iUserRepo.findByUsername(clientId)!=null){
            throw new UnauthorizedException("User does not have permission to register a new user");
        }

        if(iUserRepo.findByUsername(userName)==null){
            User user = new User();
            user.setUsername(userName);
            user.setPassword(passwordEncoder.encode(password));
            iUserRepo.save(user);
        }else{
            throw new TwitterApplicationException("Username already exists. Please try another username",HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Makes an entry in followers table indicating
     * current user to follow the toFollowUser
     *
     *
     * @param username
     * @param toFollowUser twitter handle of the user to be followed
     *
     */

    public void followUser(String username, String toFollowUser) throws TwitterApplicationException {

        User followTo = getUser(toFollowUser);
        User followBy = getUser(username);

        if(followTo.getId()==followBy.getId())
            throw new TwitterApplicationException("You cannot follow yourself",HttpStatus.BAD_REQUEST);

        if(iFollowersRepo.findByFollowByAndFollowTo(followBy,followTo).isPresent()){
            throw new TwitterApplicationException("You already follow the user", HttpStatus.BAD_REQUEST);
        }else{
            Followers followers = new Followers();
            followers.setFollowBy(followBy);
            followers.setFollowTo(followTo);
            iFollowersRepo.save(followers);
        }


    }

    /**
     * Deletes the entry in followers table indicating
     * current user to unfollow the toFollowUser
     *
     *
     * @param username
     * @param toFollowUser twitter handle of the user to be un-followed
     *
     */

    public void unFollowUser(String username, String toFollowUser) throws TwitterApplicationException {

        User followTo = getUser(toFollowUser);
        User followBy = getUser(username);

        Optional<Followers> followers = iFollowersRepo.findByFollowByAndFollowTo(followBy,followTo);
        if(followers.isPresent()){
            iFollowersRepo.delete(followers.get());
        }else{
            throw new TwitterApplicationException("You cannot un-follow a user you don't follow",HttpStatus.BAD_REQUEST);
        }

    }

    public User getUser(String username) throws TwitterApplicationException {
        User user = iUserRepo.findByUsername(username);

        if(user==null){
            throw new TwitterApplicationException(Constants.USER_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }

        return user;
    }

}

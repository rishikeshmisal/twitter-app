package com.twitter.twitterapp.controller;


import com.twitter.twitterapp.exception.TwitterApplicationException;
import com.twitter.twitterapp.helper.Constants;
import com.twitter.twitterapp.service.TweetService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping(value = Constants.BASE_MAPPING)
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @ApiOperation(
            value = "tweet",
            produces = "application/json",
            notes = "Register a tweet for a user")
    @RequestMapping(value = "/tweet",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity saveTweet(
            @ApiParam(name = "Http request ",value = "to get the current user context")
                    HttpServletRequest httpServletRequest,
            @ApiParam(name = "tweet ",value = "tweet string send by the user")
            @RequestParam("tweet") String tweetStr) throws TwitterApplicationException {

        Principal principal = httpServletRequest.getUserPrincipal();
        tweetService.saveTweet(principal.getName(),tweetStr);
        return new ResponseEntity("Tweet saved successfully", HttpStatus.OK);


    }

    @ApiOperation(
            value = "/user/timeline",
            produces = "application/json",
            notes = "GET user tweets ")
    @RequestMapping(value = "/user/timeline",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getUserTimeline(
            @ApiParam(name = "Http request ",value = "to get the current user context")
                    HttpServletRequest httpServletRequest) throws TwitterApplicationException {

        Principal principal = httpServletRequest.getUserPrincipal();
        return new ResponseEntity(tweetService.getTweets(principal.getName()), HttpStatus.OK);


    }

    @ApiOperation(
            value = "/tweet/{tweetId}",
            produces = "application/json",
            notes = "GET a tweet which was registered by the user")
    @RequestMapping(value = "/tweet/{tweetId}",method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteTweet(
            @ApiParam(name = "Http request ",value = "to get the current user context")
                    HttpServletRequest httpServletRequest,
            @ApiParam(name = "Tweet Id",value = "tweet id")
            @PathVariable("tweetId")String tweetId) throws TwitterApplicationException {

        Principal principal = httpServletRequest.getUserPrincipal();
        tweetService.deleteTweet(principal.getName(),tweetId);
        return new ResponseEntity("Tweet deleted successfully", HttpStatus.OK);

    }

    @ApiOperation(
            value = "/tweet/{tweetId}/like",
            produces = "application/json",
            notes = "Register a like for a tweet against a user")
    @RequestMapping(value = "/tweet/{tweetId}/like",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity likeTweet(
            @ApiParam(name = "Http request ",value = "to get the current user context")
                    HttpServletRequest httpServletRequest,
            @ApiParam(name = "Tweet Id",value = "tweet id")
            @PathVariable("tweetId")String tweetId) throws TwitterApplicationException {
        Principal principal = httpServletRequest.getUserPrincipal();
        tweetService.likeTweet(principal.getName(),tweetId);
        return new ResponseEntity("Tweet liked successfully", HttpStatus.OK);

    }

    @ApiOperation(
            value = "/tweet/{tweetId}/unlike",
            produces = "application/json",
            notes = "Remove the like for a tweet registered against a user")
    @RequestMapping(value = "/tweet/{tweetId}/unlike",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity unlikeTweet(
            @ApiParam(name = "Http request ",value = "to get the current user context")
                    HttpServletRequest httpServletRequest,
            @ApiParam(name = "Tweet Id",value = "tweet id")
            @PathVariable("tweetId")String tweetId) throws TwitterApplicationException {

        Principal principal = httpServletRequest.getUserPrincipal();
        tweetService.unlikeTweet(principal.getName(),tweetId);
        return new ResponseEntity("Tweet un-liked successfully", HttpStatus.OK);

    }
}

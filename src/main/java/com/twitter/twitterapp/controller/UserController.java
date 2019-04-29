package com.twitter.twitterapp.controller;

import com.twitter.twitterapp.exception.TwitterApplicationException;
import com.twitter.twitterapp.helper.Constants;
import com.twitter.twitterapp.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;


@RestController
@RequestMapping(value = Constants.BASE_MAPPING)
public class UserController {

    @Autowired
    private UserService userService;



    @ApiOperation(
            value = "register",
            produces = "application/json",
            notes = "This route registers a user")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResponseEntity userRegistration(
            @ApiParam(name = "Http request ",value = "to get the current user context")
                    HttpServletRequest httpServletRequest,
            @ApiParam(name = "user", value = "User twitter handle")
            @RequestParam("user") String userName, @RequestParam("pass") String password) throws TwitterApplicationException {
        userService.registerUser(httpServletRequest.getUserPrincipal().getName(),userName,password);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(
            value = "follow",
            produces = "application/json",
            notes = "To follow a user")
    @RequestMapping(value = "/follow",method = RequestMethod.POST)
    public ResponseEntity followUser(
            @ApiParam(name = "Http request ",value = "to get the current user context")
                    HttpServletRequest httpServletRequest,
            @ApiParam(name = "user", value = "User twitter handle")
            @RequestParam("user") String followTo) throws TwitterApplicationException {

        Principal principal = httpServletRequest.getUserPrincipal();
        userService.followUser(principal.getName(),followTo);
        return new ResponseEntity("Success", HttpStatus.OK);

    }

    @ApiOperation(
            value = "unfollow",
            produces = "application/json",
            notes = "To unfollow a user")
    @RequestMapping(value = "/unfollow",method = RequestMethod.POST)
    public ResponseEntity unFollowUser(
            @ApiParam(name = "Http request ",value = "to get the current user context")
                    HttpServletRequest httpServletRequest,
            @ApiParam(name = "user", value = "User twitter handle")
            @RequestParam("user") String followTo) throws TwitterApplicationException {
        Principal principal = httpServletRequest.getUserPrincipal();
        userService.unFollowUser(principal.getName(),followTo);
        return new ResponseEntity("Success", HttpStatus.OK);

    }


}

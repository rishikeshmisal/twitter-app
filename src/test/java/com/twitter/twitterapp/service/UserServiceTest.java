package com.twitter.twitterapp.service;

import com.twitter.twitterapp.entity.User;
import com.twitter.twitterapp.exception.TwitterApplicationException;
import com.twitter.twitterapp.repository.IFollowersRepo;
import com.twitter.twitterapp.repository.IUserRepo;
import com.twitter.twitterapp.utils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    IUserRepo iUserRepo;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    IFollowersRepo iFollowersRepo;

    @Before
    public void intializeMockito() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegisterUserPass()throws TwitterApplicationException {
        Mockito.when(passwordEncoder.encode(TestUtils.PASSWORD)).thenReturn("ENCODED-PASS");
        Mockito.when(iUserRepo.findByUsername(TestUtils.USERNAME)).thenReturn(null);
        Mockito.when(iUserRepo.findByUsername(TestUtils.USERNAME1)).thenReturn(null);
        Mockito.when(iUserRepo.save(TestUtils.getUserObject())).thenReturn(null);
        userService.registerUser(TestUtils.USERNAME1,TestUtils.USERNAME,TestUtils.PASSWORD);
    }

    @Test(expected = TwitterApplicationException.class)
    public void testRegisterUserFail()throws TwitterApplicationException {
        Mockito.when(iUserRepo.findByUsername(TestUtils.USERNAME)).thenReturn(TestUtils.getUserObject());
        Mockito.when(iUserRepo.findByUsername(TestUtils.USERNAME1)).thenReturn(null);
        userService.registerUser(TestUtils.USERNAME1,TestUtils.USERNAME,TestUtils.PASSWORD);
    }

    @Test
    public void testGetUserPass()throws TwitterApplicationException {
        Mockito.when(iUserRepo.findByUsername(TestUtils.USERNAME)).thenReturn(TestUtils.getUserObject());
        Assert.assertNotNull(userService.getUser(TestUtils.getUserObject().getUsername()));
    }

    @Test(expected = TwitterApplicationException.class)
    public void testGetUserFail()throws TwitterApplicationException {
        Mockito.when(iUserRepo.findByUsername(TestUtils.USERNAME)).thenReturn(null);
        userService.getUser(TestUtils.getUserObject().getUsername());
    }

    @Test(expected = TwitterApplicationException.class)
    public void testFollowUserFail()throws TwitterApplicationException {
        Mockito.when(iUserRepo.findByUsername(TestUtils.USERNAME)).thenReturn(TestUtils.getUserObject());
        Mockito.when(iUserRepo.findByUsername(TestUtils.USERNAME)).thenReturn(TestUtils.getUserObject());
        userService.followUser(TestUtils.getUserObject().getUsername(),TestUtils.getUserObject().getUsername());
    }

    @Test(expected = TwitterApplicationException.class)
    public void testFollowUserFail1()throws TwitterApplicationException {
        Mockito.when(iUserRepo.findByUsername(TestUtils.USERNAME)).thenReturn(TestUtils.getUserObject());
        Mockito.when(iUserRepo.findByUsername(TestUtils.USERNAME1)).thenReturn(TestUtils.getUserObject1());
        Mockito.when(iFollowersRepo.findByFollowByAndFollowTo(Mockito.any(User.class),Mockito.any(User.class))).thenReturn(TestUtils.getFollowersObject());
        userService.followUser(TestUtils.getUserObject().getUsername(),TestUtils.getUserObject1().getUsername());
    }

    @Test
    public void testFollowUserPass()throws TwitterApplicationException {
        Mockito.when(iUserRepo.findByUsername(TestUtils.USERNAME)).thenReturn(TestUtils.getUserObject());
        Mockito.when(iUserRepo.findByUsername(TestUtils.USERNAME1)).thenReturn(TestUtils.getUserObject1());
        Mockito.when(iFollowersRepo.findByFollowByAndFollowTo(TestUtils.getUserObject(),TestUtils.getUserObject1())).thenReturn(TestUtils.getFollowersObject());
        userService.followUser(TestUtils.getUserObject().getUsername(),TestUtils.getUserObject1().getUsername());
    }

    @Test
    public void testUnFollowUserPass()throws TwitterApplicationException {
        Mockito.when(iUserRepo.findByUsername(TestUtils.USERNAME)).thenReturn(TestUtils.getUserObject());
        Mockito.when(iUserRepo.findByUsername(TestUtils.USERNAME1)).thenReturn(TestUtils.getUserObject1());
        Mockito.when(iFollowersRepo.findByFollowByAndFollowTo(Mockito.any(User.class),Mockito.any(User.class))).thenReturn(TestUtils.getFollowersObject());
        userService.unFollowUser(TestUtils.getUserObject().getUsername(),TestUtils.getUserObject1().getUsername());
    }

    @Test(expected = TwitterApplicationException.class)
    public void testUnFollowUserFail()throws TwitterApplicationException {
        Mockito.when(iUserRepo.findByUsername(TestUtils.USERNAME)).thenReturn(TestUtils.getUserObject());
        Mockito.when(iUserRepo.findByUsername(TestUtils.USERNAME1)).thenReturn(TestUtils.getUserObject1());
        Mockito.when(iFollowersRepo.findByFollowByAndFollowTo(Mockito.any(User.class),Mockito.any(User.class))).thenReturn(TestUtils.getFollowersEmpty());
        userService.unFollowUser(TestUtils.getUserObject().getUsername(),TestUtils.getUserObject1().getUsername());
    }

}

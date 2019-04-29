package com.twitter.twitterapp.service;

import com.twitter.twitterapp.exception.TwitterApplicationException;
import com.twitter.twitterapp.repository.ITweetLikes;
import com.twitter.twitterapp.repository.ITweetsRepo;
import com.twitter.twitterapp.repository.IUserRepo;
import com.twitter.twitterapp.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class TweetServiceTest {

    @Mock
    IUserRepo iUserRepo;

    @Mock
    ITweetsRepo iTweetsRepo;

    @Mock
    UserService userService;

    @InjectMocks
    TweetService tweetService;

    @Mock
    ITweetLikes iTweetLikes;

    @Before
    public void intializeMockito() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = TwitterApplicationException.class)
    public void testSaveTweetFail()throws TwitterApplicationException {
       Mockito.when(userService.getUser(Mockito.anyString())).thenReturn(TestUtils.getUserObject());
       tweetService.saveTweet(TestUtils.USERNAME,TestUtils.TWEET_EXTRA);
    }

    @Test
    public void testSaveTweetPass()throws TwitterApplicationException {
        Mockito.when(userService.getUser(Mockito.anyString())).thenReturn(TestUtils.getUserObject());
        tweetService.saveTweet(TestUtils.USERNAME,TestUtils.TWEET);
    }

    @Test
    public void testDeleteTweetPass()throws TwitterApplicationException {
        Mockito.when(userService.getUser(Mockito.anyString())).thenReturn(TestUtils.getUserObject());
        Mockito.when(iTweetsRepo.findById(Long.parseLong(TestUtils.TWEET_ID_PASS))).thenReturn(TestUtils.getTweetsObject());
        //Mockito.doNothing().when(iTweetLikes.deleteAllLikesForTweet(Mockito.anyLong()));
        tweetService.deleteTweet(TestUtils.USERNAME,TestUtils.TWEET_ID_PASS);
    }

    @Test(expected = TwitterApplicationException.class)
    public void testDeleteTweetFail()throws TwitterApplicationException {
        Mockito.when(userService.getUser(Mockito.anyString())).thenReturn(TestUtils.getUserObject());
        Mockito.when(iTweetsRepo.findById(Long.parseLong(TestUtils.TWEET_ID_PASS))).thenReturn(TestUtils.getTweetEmpty());
        tweetService.deleteTweet(TestUtils.USERNAME,TestUtils.TWEET_ID_PASS);
    }

    @Test(expected = TwitterApplicationException.class)
    public void testDeleteTweetFail1()throws TwitterApplicationException {
        Mockito.when(userService.getUser(Mockito.anyString())).thenReturn(TestUtils.getUserObject1());
        Mockito.when(iTweetsRepo.findById(Long.parseLong(TestUtils.TWEET_ID_PASS))).thenReturn(TestUtils.getTweetEmpty());
        tweetService.deleteTweet(TestUtils.USERNAME1,TestUtils.TWEET_ID_PASS);
    }
}

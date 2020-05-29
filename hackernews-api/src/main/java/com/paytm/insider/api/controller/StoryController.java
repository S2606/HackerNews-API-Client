package com.paytm.insider.api.controller;

import com.paytm.insider.api.model.PastStoriesResponse;
import com.paytm.insider.api.model.TopStoriesResponse;
import com.paytm.insider.api.model.TopStoriesResponseInner;
import com.paytm.insider.service.business.StoryService;
import com.paytm.insider.service.entity.HNStoryEntity;
import com.paytm.insider.service.exceptions.HNStoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/")
public class StoryController {

    @Autowired
    private StoryService storyService;

    /**
     * Endpoint for fetching top stories(based on score) in last 10 mins
     * @return
     * @throws HNStoryNotFoundException
     */
    @RequestMapping(method = RequestMethod.GET, path = "/top-stories", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<TopStoriesResponse> fetchTopStories() throws HNStoryNotFoundException {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());
        final List<HNStoryEntity> top_story_list = storyService.getStoriesByRange(now);
        TopStoriesResponse topStories = new TopStoriesResponse();
        for(HNStoryEntity storyEntity: top_story_list){
            final TopStoriesResponseInner topStory = new TopStoriesResponseInner();
            topStory.setTitle(storyEntity.getTitle());
            topStory.setUrl(storyEntity.getUrl());
            topStory.setScore(storyEntity.getScore());
            topStory.setSubmissionTime(storyEntity.getSubmission_time()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss")));
            topStory.setUser(storyEntity.getUser().getUser_handle());
            topStories.add(topStory);
        }
        return new ResponseEntity(topStories, HttpStatus.OK);
    }

    /**
     * Endpoint for fetching previously served top stories
     * @return
     * @throws HNStoryNotFoundException
     */
    @RequestMapping(method = RequestMethod.GET, path = "/past-stories", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PastStoriesResponse> fetchPastStories() throws HNStoryNotFoundException{
        final List<HNStoryEntity> past_story_list = storyService.getPastStories();
        PastStoriesResponse pastStories = new PastStoriesResponse();
        for(HNStoryEntity storyEntity: past_story_list){
            final TopStoriesResponseInner topStory = new TopStoriesResponseInner();
            topStory.setTitle(storyEntity.getTitle());
            topStory.setUrl(storyEntity.getUrl());
            topStory.setScore(storyEntity.getScore());
            topStory.setSubmissionTime(storyEntity.getSubmission_time()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss")));
            topStory.setUser(storyEntity.getUser().getUser_handle());
            pastStories.add(topStory);
        }
        return new ResponseEntity(pastStories, HttpStatus.OK);
    }

}

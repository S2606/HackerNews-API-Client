package com.paytm.insider.service.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paytm.insider.service.common.CommentCacheBuffer;
import com.paytm.insider.service.common.StoryCacheBuffer;
import com.paytm.insider.service.common.Utils;
import com.paytm.insider.service.dao.HNCommentDao;
import com.paytm.insider.service.dao.HNStoryDao;
import com.paytm.insider.service.dao.HNUserDao;
import com.paytm.insider.service.dto.HNCommentDto;
import com.paytm.insider.service.dto.HNStoryDto;
import com.paytm.insider.service.dto.HNUserDto;
import com.paytm.insider.service.entity.HNCommentEntity;
import com.paytm.insider.service.entity.HNStoryEntity;
import com.paytm.insider.service.entity.HNUserEntity;
import com.paytm.insider.service.exceptions.URLRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;

import static com.paytm.insider.service.common.Utils.covertUnixtoZoneDateTime;
import static com.paytm.insider.service.common.Utils.fetchDataFromURL;

@Service
@Transactional
public class DataUpdaterService {

    @Autowired
    private HNStoryDao hnStoryDao;

    @Autowired
    private HNCommentDao hnCommentDao;

    @Autowired
    private HNUserDao hnUserDao;

    private ObjectMapper objectMapper;

    private CommentCacheBuffer commentCacheBuffer;

    private StoryCacheBuffer storyCacheBuffer;

    public DataUpdaterService(ObjectMapper objectMapper, CommentCacheBuffer commentCacheBuffer, StoryCacheBuffer storyCacheBuffer){
        this.objectMapper = objectMapper;
        this.commentCacheBuffer = commentCacheBuffer;
        this.storyCacheBuffer = storyCacheBuffer;
    }

    /**
     * Fetching top stories from hackerNews URL
     */
    public void fetchStories(){
        ArrayList<String> id_list;
        ArrayList response_payload;
        id_list = fetchStoriesbyId();
        if(id_list!=null) {
            for (String id : id_list) {
                String urlString = Utils.generateHackerNewsUrl(
                        id.replace("[", "").replace("]", ""), "item");
                String response = null;
                try {
                    response = fetchDataFromURL(urlString);
                    storyCacheBuffer.addToBuffer(objectMapper.readValue(response, HNStoryDto.class));
                } catch (IOException | URLRequestException e) {
                    //Error in fetching story or connection request
                    continue;
                }

            }
            response_payload = storyCacheBuffer.getQueueBuffer();
            storyCacheBuffer.clearBuffer();
            persistStoryDataintoDB(response_payload);
            fetchCommentForStory(response_payload);
        }

    }

    /**
     * Fetch top stories from https://hacker-news.firebaseio.com/v0/topstories.json API
     * @return ArrayList<String> List of top story ID's
     */
    protected ArrayList<String> fetchStoriesbyId(){
        try {
            ArrayList<String> output_list = new ArrayList<String>();
            String response = fetchDataFromURL("https://hacker-news.firebaseio.com/v0/topstories.json");
            Collections.addAll(output_list, response.split(","));
            return output_list;
        } catch (URLRequestException | IOException e){
            //Error in connection request or in fetching stories
            return null;
        }

    }

    /**
     * Persist the given Story data to the DB.
     * @param payload
     */
    @Transactional(propagation = Propagation.REQUIRED)
    protected void persistStoryDataintoDB(ArrayList<HNStoryDto> payload){
        for(HNStoryDto hnStory: payload){
            HNStoryEntity story = hnStoryDao.getStoryById(hnStory.getId());
            HNUserEntity user = createOrFetchUserEntity(hnStory.getBy());
            if(!hnStory.getType().equals("poll") && story==null && user!=null) {
                final HNStoryEntity hnStoryEntity = new HNStoryEntity();
                hnStoryEntity.setId(hnStory.getId());
                hnStoryEntity.setTitle(hnStory.getTitle());
                hnStoryEntity.setUrl(hnStory.getUrl());
                hnStoryEntity.setScore(hnStory.getScore());
                hnStoryEntity.setSubmission_time(covertUnixtoZoneDateTime(hnStory.getTime()));
                hnStoryEntity.setUser(user);
                hnStoryEntity.setCreated_at(ZonedDateTime.now());
                hnStoryDao.createStory(hnStoryEntity);
            }
        }
    }

    /**
     * Fetch comment data for given stories
     * @param payload Data Transfer Objects of stories
     */
    protected void fetchCommentForStory(ArrayList<HNStoryDto> payload){
        for(HNStoryDto hnStory: payload){
            ArrayList response_payload = new ArrayList<>();
            String[] comment_id_list = hnStory.getKids();
            if(comment_id_list!=null) {
                for (String comment_id : comment_id_list) {
                    String urlString = Utils.generateHackerNewsUrl(comment_id, "item");
                    String response = null;
                    try {
                        response = fetchDataFromURL(urlString);
                        commentCacheBuffer.addToBuffer(objectMapper.readValue(response, HNCommentDto.class));
                    } catch (IOException | URLRequestException e) {
                        //Error in fetching story or connection request
                        continue;
                    }
                }
                response_payload = commentCacheBuffer.getQueueBuffer();
                commentCacheBuffer.clearBuffer();
                persistCommentDataintoDB(response_payload);
            }
        }
    }

    /**
     * Persist Comment Data into DB
     * @param payload
     */
    @Transactional(propagation = Propagation.REQUIRED)
    protected void persistCommentDataintoDB(ArrayList<HNCommentDto> payload){
        for(HNCommentDto comment: payload){
            HNCommentEntity getComment = hnCommentDao.getCommentById(comment.getId());
            if(comment.getBy()==null){
                //Can't really add a null entry in foreign key field. So no null references for comments author"
                continue;
            } else if(getComment!=null) {
                //Found a comment duplicate entry
                continue;
            } else {
                HNStoryEntity story = hnStoryDao.getStoryById(comment.getParent());
                HNUserEntity user = createOrFetchUserEntity(comment.getBy());
                if (story.getId() != null) {
                    final HNCommentEntity hnComment = new HNCommentEntity();
                    hnComment.setId(comment.getId());
                    hnComment.setComment_text(comment.getText());
                    hnComment.setComment_time(covertUnixtoZoneDateTime(comment.getTime()));
                    hnComment.setComment_count(comment.getKids() == null ? 0 : comment.getKids().length);
                    hnComment.setStory(story);
                    hnComment.setUser(user);
                    hnCommentDao.createComment(hnComment);
                }
            }
        }
    }

    /**
     * Fetch user's HN Age i.e. basically how old their profile is in years
     * @param userHandle
     * @return
     */
    protected int fetchUserAge(String userHandle){
        String urlString = Utils.generateHackerNewsUrl(userHandle, "user");
        HNUserDto response_dto = null;
        String response = null;
        try {
            response = fetchDataFromURL(urlString);
            response_dto = objectMapper.readValue(response, HNUserDto.class);
        } catch (IOException | URLRequestException e) {
            //Error in fetching story or connection request
        }

        ZonedDateTime created_At = Utils.covertUnixtoZoneDateTime(response_dto.getCreated());
        return Utils.getYearsfromZonedTime(created_At);
    }

    /**
     * Function to either create a new user or fetch user from DB
     * @param user_handle
     * @return
     */
    protected HNUserEntity createOrFetchUserEntity(String user_handle){
        HNUserEntity user = hnUserDao.getUserByHandle(user_handle);
        if(user==null){
            HNUserEntity newUser = new HNUserEntity();
            newUser.setUser_handle(user_handle);
            newUser.setHn_age(fetchUserAge(user_handle));
            user = hnUserDao.createUser(newUser);
        }
        return user;
    }

}


package com.paytm.insider.api.controller;

import com.paytm.insider.api.model.TopCommentsResponse;
import com.paytm.insider.api.model.TopCommentsResponseInner;
import com.paytm.insider.service.business.CommentService;
import com.paytm.insider.service.entity.HNCommentEntity;
import com.paytm.insider.service.exceptions.HNCommentFromStoryIDNotGivenException;
import com.paytm.insider.service.exceptions.HNCommentNotFoundException;
import com.paytm.insider.service.exceptions.HNStoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * Endpoint for fetching top comments(based on comment count) for a story
     * @param storyId
     * @return
     * @throws HNStoryNotFoundException
     * @throws HNCommentNotFoundException
     * @throws HNCommentFromStoryIDNotGivenException
     */
    @RequestMapping(method = RequestMethod.GET, path = "/comments/{storyId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<TopCommentsResponse> fetchTopComments(@PathVariable("storyId") final Integer storyId)
            throws HNStoryNotFoundException, HNCommentNotFoundException, HNCommentFromStoryIDNotGivenException {
        final List<HNCommentEntity> topCommentlist = commentService.getCommentbyCommentCount(storyId);
        TopCommentsResponse topComments = new TopCommentsResponse();
        for(HNCommentEntity commentEntity: topCommentlist){
            final TopCommentsResponseInner topComment = new TopCommentsResponseInner();
            topComment.setText(commentEntity.getComment_text());
            topComment.setUserHandle(commentEntity.getUser().getUser_handle());
            topComment.setHnAge(commentEntity.getUser().getHn_age());
            topComments.add(topComment);
        }
        return new ResponseEntity(topComments, HttpStatus.OK);
    }
}

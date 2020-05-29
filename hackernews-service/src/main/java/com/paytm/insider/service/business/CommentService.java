package com.paytm.insider.service.business;

import com.paytm.insider.service.dao.HNCommentDao;
import com.paytm.insider.service.dao.HNStoryDao;
import com.paytm.insider.service.entity.HNCommentEntity;
import com.paytm.insider.service.entity.HNStoryEntity;
import com.paytm.insider.service.exceptions.HNCommentFromStoryIDNotGivenException;
import com.paytm.insider.service.exceptions.HNCommentNotFoundException;
import com.paytm.insider.service.exceptions.HNStoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private HNCommentDao commentDao;

    @Autowired
    private HNStoryDao storyDao;

    /**
     * Business Logic for fetching comments for a story
     * @param storyId
     * @return
     * @throws HNStoryNotFoundException
     * @throws HNCommentNotFoundException
     * @throws HNCommentFromStoryIDNotGivenException
     */
    public List<HNCommentEntity> getCommentbyCommentCount(final Integer storyId)
            throws HNStoryNotFoundException, HNCommentNotFoundException, HNCommentFromStoryIDNotGivenException {
        if(storyId==null){
            throw new HNCommentFromStoryIDNotGivenException("No ID is given");
        }
        HNStoryEntity storyEntity = storyDao.getStoryById(storyId);
        if(storyEntity!=null){
            List<HNCommentEntity> commentList = commentDao.getCommentlistByCount(storyEntity);
            if(commentList==null || commentList.size()==0){
                throw new HNCommentNotFoundException("Comment Not Found For Story ID "+storyId);
            }
            return commentList;
        }else{
            throw new HNStoryNotFoundException("Story Not Found for ID "+storyId);
        }
    }

}

package com.paytm.insider.service.business;

import com.paytm.insider.service.dao.HNStoryDao;
import com.paytm.insider.service.entity.HNStoryEntity;
import com.paytm.insider.service.exceptions.HNStoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class StoryService {

    @Autowired
    private HNStoryDao storyDao;

    /**
     * Business Logic for fetching top stories based on descending order of scores in last 10 mins
     * @param givenDateTime
     * @return
     * @throws HNStoryNotFoundException
     */
    public List<HNStoryEntity> getStoriesByRange(ZonedDateTime givenDateTime) throws HNStoryNotFoundException{
        List<HNStoryEntity> storyEntityList =  storyDao.getStoryListByRange(givenDateTime);
        if(storyEntityList==null || storyEntityList.size()==0){
            throw new HNStoryNotFoundException("Story was not found for time "+givenDateTime
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss")));
        } else {
            return storyEntityList;
        }
    }

    /**
     * Business Logic for fetching past top stories
     * @return
     * @throws HNStoryNotFoundException
     */
    public List<HNStoryEntity> getPastStories() throws HNStoryNotFoundException{
        List<HNStoryEntity> storyEntityList = storyDao.getPastStoriesList();
        if(storyEntityList==null || storyEntityList.size()==0){
            throw new HNStoryNotFoundException("Story was not found");
        } else {
            return storyEntityList;
        }
    }
}

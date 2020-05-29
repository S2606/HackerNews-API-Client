package com.paytm.insider.service.dao;

import com.paytm.insider.service.entity.HNStoryEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class HNStoryDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Function to create story
     * @param storyEntity
     * @return
     */
    public HNStoryEntity createStory(HNStoryEntity storyEntity)
    {
        entityManager.persist(storyEntity);
        return storyEntity;
    }

    /**
     * Function to get story by name
     * @param Id
     * @return
     */
    public HNStoryEntity getStoryById(final Integer Id) {
        try {
            return entityManager
                    .createNamedQuery("storyById", HNStoryEntity.class)
                    .setParameter("id",Id).getSingleResult();
        }
        catch (NoResultException nre)
        {
            return null;
        }
    }

    /**
     * Function to get list of stories based on a time period
     * @param zonedDateTime
     * @return
     */
    public List<HNStoryEntity> getStoryListByRange(ZonedDateTime zonedDateTime) {
        try {
            List<HNStoryEntity> result =  entityManager
                    .createQuery("select hn from HNStoryEntity hn where hn.created_at >=:interval_timestamp and hn.created_at<:current_timestamp order by hn.score desc")
                    .setParameter("interval_timestamp", zonedDateTime.minusMinutes(10))
                    .setParameter("current_timestamp", zonedDateTime)
                    .setMaxResults(10)
                    .getResultList();
            return  result;
        }
        catch (NoResultException nre)
        {
            return null;
        }
    }

    public List<HNStoryEntity> getPastStoriesList() {
        try {
            List<HNStoryEntity> result =  entityManager
                    .createQuery("select hn from HNStoryEntity hn")
                    .getResultList();
            return  result;
        }
        catch (NoResultException nre)
        {
            return null;
        }
    }
}

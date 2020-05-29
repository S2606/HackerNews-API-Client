package com.paytm.insider.service.dao;

import com.paytm.insider.service.entity.HNCommentEntity;
import com.paytm.insider.service.entity.HNStoryEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class HNCommentDao {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Function for Creating Comment
     * @param commentEntity
     * @return
     */
    public HNCommentEntity createComment(HNCommentEntity commentEntity)
    {
        entityManager.persist(commentEntity);
        return commentEntity;
    }

    /**
     * Function to get Comment by ID
     * @param Id
     * @return
     */
    public HNCommentEntity getCommentById(final Integer Id) {
        try {
            return entityManager
                    .createNamedQuery("commentById", HNCommentEntity.class)
                    .setParameter("id",Id).getSingleResult();
        }
        catch (NoResultException nre)
        {
            return null;
        }
    }

    /**
     * Function to get List of comments for a particular story
     * @param storyEntity
     * @return
     */
    public List<HNCommentEntity> getCommentlistByCount(HNStoryEntity storyEntity) {
        try {
            List<HNCommentEntity> result =  entityManager
                    .createQuery("select hn from HNCommentEntity hn where hn.story <=:story order by hn.comment_count desc")
                    .setParameter("story", storyEntity)
                    .setMaxResults(10)
                    .getResultList();
            return  result;
        }
        catch (NoResultException nre)
        {
            return null;
        }
    }
}

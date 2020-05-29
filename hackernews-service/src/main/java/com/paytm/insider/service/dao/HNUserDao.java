package com.paytm.insider.service.dao;

import com.paytm.insider.service.entity.HNUserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class HNUserDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Function to create User
     * @param HNUserEntity
     * @return
     */
    public HNUserEntity createUser(HNUserEntity HNUserEntity)
    {
        entityManager.persist(HNUserEntity);
        return HNUserEntity;
    }

    /**
     * Function to get User by name
     * @param userHandle
     * @return
     */
    public HNUserEntity getUserByHandle(final String userHandle) {
        try {
            return entityManager.createNamedQuery("userByHandle", HNUserEntity.class).setParameter("handle",userHandle).getSingleResult();
        }
        catch (NoResultException nre)
        {
            return null;
        }
    }
}


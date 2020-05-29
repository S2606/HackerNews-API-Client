package com.paytm.insider.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Table(name = "hn_comment", schema = "public")
@NamedQueries(
        {
                @NamedQuery(name = "commentById", query = "select u from HNCommentEntity u where u.id =:id")
        })
public class HNCommentEntity implements Serializable {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "comment_text")
    @NotNull
    private String comment_text;

    @Column(name = "comment_time")
    @NotNull
    private ZonedDateTime comment_time;

    @Column(name = "comment_count")
    @NotNull
    private Integer comment_count;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private HNUserEntity user;

    @ManyToOne
    @JoinColumn(name = "story_id")
    private HNStoryEntity story;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String text) {
        this.comment_text = text;
    }

    public ZonedDateTime getComment_time() {
        return comment_time;
    }

    public void setComment_time(ZonedDateTime comment_time) {
        this.comment_time = comment_time;
    }

    public Integer getComment_count() {
        return comment_count;
    }

    public void setComment_count(Integer comment_count) {
        this.comment_count = comment_count;
    }

    public HNStoryEntity getStory() {
        return story;
    }

    public void setStory(HNStoryEntity story) {
        this.story = story;
    }

    public HNUserEntity getUser() {
        return user;
    }

    public void setUser(HNUserEntity user) {
        this.user = user;
    }
}

package com.paytm.insider.service.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Table(name = "hn_story", schema = "public")
@NamedQueries(
        {
                @NamedQuery(name = "storyById", query = "select u from HNStoryEntity u where u.id =:id"),
        })
public class HNStoryEntity implements Serializable {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    @NotNull
    @Size(max = 200)
    private String title;

    @Column(name = "url")
    @Size(max = 200)
    private String url;

    @Column(name = "score")
    @NotNull
    private Integer score;

    @Column(name = "submission_time")
    @NotNull
    private ZonedDateTime submission_time;

    @Column(name = "created_at")
    @NotNull
    private ZonedDateTime created_at;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private HNUserEntity user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public ZonedDateTime getSubmission_time() {
        return submission_time;
    }

    public void setSubmission_time(ZonedDateTime submission_time) {
        this.submission_time = submission_time;
    }

    @Override
    public boolean equals(Object obj) {
        return new EqualsBuilder().append(this, obj).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this).hashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public HNUserEntity getUser() {
        return user;
    }

    public void setUser(HNUserEntity user) {
        this.user = user;
    }

    public ZonedDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(ZonedDateTime created_at) {
        this.created_at = created_at;
    }
}



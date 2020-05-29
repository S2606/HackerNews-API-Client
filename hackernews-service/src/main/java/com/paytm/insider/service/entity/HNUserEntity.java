package com.paytm.insider.service.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "hn_users", schema = "public")
@NamedQueries(
        {
                @NamedQuery(name = "userByHandle", query = "select u from HNUserEntity u where u.user_handle =:handle")
        })
public class HNUserEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_handle")
    @Size(max = 200)
    private String user_handle;

    @Column(name = "hn_age")
    private Integer hn_age;

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

    public Integer getHn_age() {
        return hn_age;
    }

    public void setHn_age(Integer hn_age) {
        this.hn_age = hn_age;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser_handle() {
        return user_handle;
    }

    public void setUser_handle(String user_handle) {
        this.user_handle = user_handle;
    }
}

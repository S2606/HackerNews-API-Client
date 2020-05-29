package com.paytm.insider.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class HNCommentDto {
    private Integer id;

    private String[] kids;

    private String by;

    private String time;

    private Integer parent;

    private String text;

    private String type;

    public Integer getId() {
        return id;
    }

    public String[] getKids() {
        return kids;
    }

    public String getBy() {
        return by;
    }

    public String getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    public Integer getParent() {
        return parent;
    }
}

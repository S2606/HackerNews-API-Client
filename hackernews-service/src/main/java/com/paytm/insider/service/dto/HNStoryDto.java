package com.paytm.insider.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class HNStoryDto {

    private Integer id;

    private String[] kids;

    private String descendants;

    private String type;

    private String title;

    private String url;

    private Integer score;

    private String time;

    private String by;

    public Integer getScore() {
        return this.score;
    }

    public String getTime() {
        return this.time;
    }

    public String getBy() {
        return this.by;
    }

    public String getUrl() {
        return this.url;
    }

    public String getTitle() {
        return this.title;
    }

    public Integer getId() {
        return this.id;
    }

    public String getDescendants() {
        return descendants;
    }

    public String[] getKids() {
        return kids;
    }

    public String getType() {
        return type;
    }
}


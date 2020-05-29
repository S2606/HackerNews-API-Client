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
public class HNUserDto {

    private String about;

    private String created;

    private String id;


    public String getAbout() {
        return about;
    }

    public String getCreated() {
        return created;
    }

    public String getId() {
        return id;
    }
}

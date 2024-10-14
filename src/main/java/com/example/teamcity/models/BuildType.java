package com.example.teamcity.models;

import com.example.teamcity.annotations.Random;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BuildType extends BaseModel{
    @Random
    private String id;
    @Random
    private String name;
    private Project project;
    private Steps steps;

}

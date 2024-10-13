package com.example.teamcity.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildType extends BaseModel{

    private String id;
    private String name;
    private Project project;
    private Steps steps;

}

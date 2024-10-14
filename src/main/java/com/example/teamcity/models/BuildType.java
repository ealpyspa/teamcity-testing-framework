package com.example.teamcity.models;

import com.example.teamcity.annotations.Optional;
import com.example.teamcity.annotations.Parameterizable;
import com.example.teamcity.annotations.Random;
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
    @Random
    private String name;
    @Parameterizable
    private Project project;
    @Optional
    private Steps steps;

}

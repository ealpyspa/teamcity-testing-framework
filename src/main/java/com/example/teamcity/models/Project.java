package com.example.teamcity.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project extends BaseModel {
    private String id;
    private String name;
    @Builder.Default
    private String locator = "_Root";
}

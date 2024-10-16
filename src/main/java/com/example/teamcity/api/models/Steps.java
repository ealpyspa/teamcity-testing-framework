package com.example.teamcity.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hamcrest.Condition;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Steps extends BaseModel {
    private Integer count;
    private List<Condition.Step> step;
}

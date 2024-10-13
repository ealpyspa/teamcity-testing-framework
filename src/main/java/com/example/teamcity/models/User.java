package com.example.teamcity.models;

import com.example.teamcity.models.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseModel {
    private String username;
    private String password;
}


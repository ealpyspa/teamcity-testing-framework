package com.example.teamcity.models;

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
public class Role extends BaseModel {
    @Builder.Default
    private String roleId = "SYSTEM_ADMIN";
    @Builder.Default
    private String scope = "g";
}
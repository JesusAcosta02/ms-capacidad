package com.onclass.mscapacidad.application.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CapacityWithTechnologiesDTO {

    private String id;
    private String name;
    private String description;
    private List<TechnologyInfo> technologies;
}
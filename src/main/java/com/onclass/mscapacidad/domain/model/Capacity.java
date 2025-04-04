package com.onclass.mscapacidad.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Capacity {

    private String id;
    private String name;
    private String description;
    private List<String> technologyIds;


}
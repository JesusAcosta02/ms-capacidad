package com.onclass.mscapacidad.domain.model;

import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Capacity {

    private String id;
    private String name;
    private String description;
    private List<String> technologyIds;



}
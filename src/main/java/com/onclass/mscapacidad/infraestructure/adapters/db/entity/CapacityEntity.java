package com.onclass.mscapacidad.infraestructure.adapters.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("capacidades")
public class CapacityEntity {

    @Id
    private String id;

    private String name;

    private String description;

    private String technologyIds;
}
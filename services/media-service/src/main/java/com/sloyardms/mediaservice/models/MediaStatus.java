package com.sloyardms.mediaservice.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "media_status")
public class MediaStatus extends BaseAuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

}

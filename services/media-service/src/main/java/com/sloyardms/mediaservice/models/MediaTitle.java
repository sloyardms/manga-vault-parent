package com.sloyardms.mediaservice.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "media_titles")
public class MediaTitle extends BaseAuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "primary")
    private boolean primary;

}

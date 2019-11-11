package com.neo.bookameetingroom.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
public class ChangeInfoRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "request_id")
    private Long id;
    @NotEmpty
    private String type;
    @NotEmpty
    private String oldOne;
    @NotEmpty
    private String newOne;
    private String status;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Person person;
}

package com.neo.bookameetingroom.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class MeetingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private String location;
    private String status;
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Facility> facilities;
}

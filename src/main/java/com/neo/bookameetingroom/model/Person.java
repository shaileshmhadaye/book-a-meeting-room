package com.neo.bookameetingroom.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "person_id")
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String department;
    private String location;
    private int active;

    @ManyToOne(cascade = CascadeType.ALL)
    private Role role;

}

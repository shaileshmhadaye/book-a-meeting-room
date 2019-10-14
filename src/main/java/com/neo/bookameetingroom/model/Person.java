package com.neo.bookameetingroom.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String department;
    private String location;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Roles role;

}

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
public class Person {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String department;
    private String location;

//    @ManyToMany(cascade=CascadeType.MERGE)
//    @JoinTable(
//            name="person_role",
//            joinColumns={@JoinColumn(name="PERSON_ID", referencedColumnName="ID")},
//            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
//    private List<Role> roles;

}

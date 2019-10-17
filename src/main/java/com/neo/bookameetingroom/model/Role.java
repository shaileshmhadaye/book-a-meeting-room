package com.neo.bookameetingroom.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private String status;

//    @ManyToMany(mappedBy="roles")
//    private List<Person> personList;
}

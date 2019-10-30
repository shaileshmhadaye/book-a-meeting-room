package com.neo.bookameetingroom.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

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
    @NotEmpty(message = "*Please provide first name")
    private String firstName;
    @NotEmpty(message = "*Please provide last name")
    private String lastName;
    @Email(message = "*Please provide valid email")
    @NotEmpty(message = "*Please provide Email")
    private String email;
    @NotEmpty(message = "*Please provide password")
    @Length(min = 6, message = "*Your password must have at least 6 characters")
    private String password;
    @NotEmpty
    private String department;
    @NotEmpty
    private String location;
    private int active;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Role role;

    @OneToMany(mappedBy = "person")
    private List<BookingDetails> bookingDetails;

}

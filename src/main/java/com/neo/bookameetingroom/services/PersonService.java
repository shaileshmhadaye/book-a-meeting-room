package com.neo.bookameetingroom.services;

import com.neo.bookameetingroom.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    Person save(Person person);
    Person update(Person person);
    List<Person> findAll();
    Optional<Person> findById(Long id);
    void deleteById(Long id);
    Person findByEmail(String email);
    Page<Person> getPaginatedUsers(Pageable pageable);
}

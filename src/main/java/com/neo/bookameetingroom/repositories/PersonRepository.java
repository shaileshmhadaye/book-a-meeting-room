package com.neo.bookameetingroom.repositories;

import com.neo.bookameetingroom.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("personRepository")
public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByEmail(String email);
    Page<Person> findAll(Pageable pageable);
}

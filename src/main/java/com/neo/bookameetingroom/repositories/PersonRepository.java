package com.neo.bookameetingroom.repositories;

import com.neo.bookameetingroom.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}

package com.neo.bookameetingroom.services;

import com.neo.bookameetingroom.model.Person;
import com.neo.bookameetingroom.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person save(Person person){
        return personRepository.save(person);
    }
}

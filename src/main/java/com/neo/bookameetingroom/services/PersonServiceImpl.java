package com.neo.bookameetingroom.services;

import com.neo.bookameetingroom.model.Person;
import com.neo.bookameetingroom.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService{

    @Autowired
    private PersonRepository personRepository;

    public Person save(Person person){
        return personRepository.save(person);
    }

    public List<Person> findAll(){
        return personRepository.findAll();
    }

    public Optional<Person> findById(Long id){
        return personRepository.findById(id);
    }

    public void deleteById(Long id){
        personRepository.deleteById(id);
    }

    public Person findByUsername(String username){
        return personRepository.findByUsername(username);
    }
}

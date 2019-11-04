package com.neo.bookameetingroom.services;

import com.neo.bookameetingroom.model.Person;
import com.neo.bookameetingroom.model.Role;
import com.neo.bookameetingroom.repositories.PersonRepository;
import com.neo.bookameetingroom.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService{

    private PersonRepository personRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository,
                             RoleRepository roleRepository,
                             BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Person save(Person person){
        //person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));
        Role personRole = roleRepository.findByRole("User");
        person.setRole(personRole);
        return personRepository.save(person);
    }

    public Person update(Person person){
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

    public Person findByEmail(String email){
        return personRepository.findByEmail(email);
    }

    @Override
    public Page<Person> getPaginatedUsers(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

}

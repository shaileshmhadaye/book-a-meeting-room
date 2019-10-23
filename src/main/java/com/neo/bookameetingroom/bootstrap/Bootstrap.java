package com.neo.bookameetingroom.bootstrap;

import com.neo.bookameetingroom.model.Person;
import com.neo.bookameetingroom.model.Role;
import com.neo.bookameetingroom.repositories.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public void run(String... args) throws Exception {

        log.debug("bootstrap file running");
//        Set<Role> roles = new HashSet<>();
//        Role role = new Role(4L,"Admin");
//        roles.add(role);
//
//        Person person = new Person();
//        person.setId(6L);
//        person.setFirstName("shailesh");
//        person.setLastName("Mhadaye");
//        person.setEmail("shailesh@gmail.com");
//        person.setDepartment("software development");
//        person.setPassword(new BCryptPasswordEncoder().encode("1234"));
//        person.setLocation("parel");
//        person.setRoles(roles);
//        person.setActive(1);
//
//        personRepository.save(person);
    }
}

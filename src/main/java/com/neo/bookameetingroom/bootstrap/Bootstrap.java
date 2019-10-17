package com.neo.bookameetingroom.bootstrap;

import com.neo.bookameetingroom.model.Person;
import com.neo.bookameetingroom.repositories.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public void run(String... args) throws Exception {

        log.debug("bootstrap file running");
        //Roles roles = new Roles(1L,"Admin","active");

        Person person = new Person();
        person.setId(6L);
        person.setFirstName("shailesh");
        person.setLastName("Mhadaye");
        person.setEmail("shailesh@gmail.com");
        person.setDepartment("software development");
        person.setUsername("test");
        person.setPassword("1234");
        person.setLocation("parel");
        //person.setRole(roles);

        personRepository.save(person);
    }
}

package com.neo.bookameetingroom.bootstrap;

import com.neo.bookameetingroom.model.Facility;
import com.neo.bookameetingroom.model.MeetingRoom;
import com.neo.bookameetingroom.model.Person;
import com.neo.bookameetingroom.model.Role;
import com.neo.bookameetingroom.repositories.FacilityRepository;
import com.neo.bookameetingroom.repositories.MeetingRoomRepository;
import com.neo.bookameetingroom.repositories.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
//@Component
public class Bootstrap implements CommandLineRunner {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MeetingRoomRepository meetingRoomRepository;

    @Autowired
    private FacilityRepository facilityRepository;

    @Override
    public void run(String... args) throws Exception {

        log.debug("bootstrap file running");

        List<Facility> facilities = new ArrayList<>();
        Facility facility = new Facility("AC");
        Facility projector = new Facility("projector");

        facilities.add(projector);
        facilities.add(facility);
        facilityRepository.saveAll(facilities);

        MeetingRoom meetingRoom = new MeetingRoom();
        meetingRoom.setId(2L);
        meetingRoom.setDescription("Green Room");
        meetingRoom.setLocation("parel");
        meetingRoom.setFacilities(facilities);

        meetingRoomRepository.save(meetingRoom);

//        Role role = new Role();
//        role.setId(4L);
//        role.setRole("Admin");
//
//        Person person = new Person();
//        person.setId(6L);
//        person.setFirstName("shailesh");
//        person.setLastName("Mhadaye");
//        person.setEmail("shailesh@gmail.com");
//        person.setDepartment("software development");
//        person.setPassword(new BCryptPasswordEncoder().encode("1234"));
//        person.setLocation("parel");
//        person.setRole(role);
//        person.setActive(1);
//
//        personRepository.save(person);
    }
}

package com.neo.bookameetingroom.controllers;

import com.neo.bookameetingroom.model.Person;
import com.neo.bookameetingroom.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping("/register")
    public String register(Model model){
        model.addAttribute("person", new Person());
        return "register";
    }

    @PostMapping("/register_person")
    public String  register(@Valid Person person){
        personRepository.save(person);
        return "login";
    }
}

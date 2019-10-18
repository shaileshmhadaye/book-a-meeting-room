//package com.neo.bookameetingroom.controllers;
//
//import com.neo.bookameetingroom.model.Person;
//import com.neo.bookameetingroom.services.PersonService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.validation.Valid;
//
//@Controller
//public class PersonController {
//
//    @Autowired
//    private PersonService personService;
//
//    @RequestMapping("/register")
//    public String register(Model model){
//        model.addAttribute("person", new Person());
//        return "register";
//    }
//
//    @PostMapping("/register_person")
//    public String  register(@Valid Person person){
//        personService.save(person);
//        return "login";
//    }
//}

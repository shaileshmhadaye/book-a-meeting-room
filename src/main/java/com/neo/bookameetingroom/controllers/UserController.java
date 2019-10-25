package com.neo.bookameetingroom.controllers;

import com.neo.bookameetingroom.model.Person;
import com.neo.bookameetingroom.services.PersonService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    private final PersonService personService;

    public UserController(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(value="/home", method = RequestMethod.GET)
    public ModelAndView userHome(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person user = personService.findByEmail(auth.getName());
        modelAndView.addObject("username", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.setViewName("user/home");
        return modelAndView;
    }

    @RequestMapping(value="/book-a-meeting-room", method = RequestMethod.GET)
    public ModelAndView bookAMeetingRoom(){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("meeting-room/book-a-meeting-room");
        return modelAndView;
    }
}

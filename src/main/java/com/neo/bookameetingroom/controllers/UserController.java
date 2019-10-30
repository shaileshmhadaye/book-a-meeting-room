package com.neo.bookameetingroom.controllers;

import com.neo.bookameetingroom.model.Person;
import com.neo.bookameetingroom.repositories.BookingDetailsRepository;
import com.neo.bookameetingroom.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    private final PersonService personService;

    @Autowired
    BookingDetailsRepository bookingDetailsRepository;

    public UserController(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(value="/home", method = RequestMethod.GET)
    public ModelAndView userHome(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person user = personService.findByEmail(auth.getName());
        modelAndView.addObject("username", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("id", user.getId());
        modelAndView.setViewName("user/home");
        return modelAndView;
    }

    @RequestMapping(value="/view-booking-request/{user_id}", method = RequestMethod.GET)
    public ModelAndView ViewBookingRequest(@PathVariable("user_id") Long id){
        ModelAndView modelAndView = new ModelAndView();
        Person user = personService.findById(id).orElse(null);
        modelAndView.addObject("bookingDetails", user.getBookingDetails());
        modelAndView.setViewName("user/booking-requests");
        return modelAndView;
    }

    @RequestMapping("/delete-request/{book_id}")
    public ModelAndView DeleteRequest(@PathVariable("book_id") Long id){
        ModelAndView modelAndView = new ModelAndView();
        bookingDetailsRepository.deleteById(id);
        modelAndView.setViewName("user/booking-requests");
        return modelAndView;
    }
}

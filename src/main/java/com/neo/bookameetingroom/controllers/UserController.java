package com.neo.bookameetingroom.controllers;

import com.neo.bookameetingroom.model.BookingDetails;
import com.neo.bookameetingroom.model.Person;
import com.neo.bookameetingroom.repositories.BookingDetailsRepository;
import com.neo.bookameetingroom.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final PersonService personService;

    @Autowired
    BookingDetailsRepository bookingDetailsRepository;

    public UserController(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(value="/view-booking-request", method = RequestMethod.GET)
    public ModelAndView ViewBookingRequest(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person user = personService.findByEmail(auth.getName());
        List<BookingDetails> bookingDetails = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (BookingDetails bookingDetail: user.getBookingDetails()) {
            if(bookingDetail.getDate().isAfter(today) || bookingDetail.getDate().isEqual(today)){
                bookingDetails.add(bookingDetail);
            }
        }
        modelAndView.addObject("temp", "1");
        modelAndView.addObject("bookingDetails", bookingDetails);
        modelAndView.setViewName("user/booking-requests");
        return modelAndView;
    }

    @RequestMapping(value = "/booking-history/{user_id}", method = RequestMethod.GET)
    public ModelAndView bookingHistory(@PathVariable("user_id") Long id){
        ModelAndView modelAndView = new ModelAndView();
        Person user = personService.findById(id).orElse(null);
        List<BookingDetails> bookingDetails = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (BookingDetails bookingDetail: user.getBookingDetails()) {
            if(bookingDetail.getDate().isBefore(today)){
                bookingDetails.add(bookingDetail);
            }
        }
        modelAndView.addObject("temp", "0");
        modelAndView.addObject("bookingDetails", bookingDetails);
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

    @RequestMapping("/user-profile/{user_id}")
    public String userProfile(@PathVariable("user_id") Long id, Model model){
        Person person = personService.findById(id).orElse(null);
        model.addAttribute("user", person);
        return "user/user-profile";
    }

    @RequestMapping("/edit-profile")
    public String editProfile( Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person user = personService.findByEmail(auth.getName());
        model.addAttribute("user", user);
        return "user/edit-profile";
    }

    @PutMapping("/edit-profile")
    public String saveProfile(@Valid Person person){
        personService.save(person);
        return "user/user-profile";
    }
}

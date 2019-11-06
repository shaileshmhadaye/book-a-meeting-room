package com.neo.bookameetingroom.controllers;

import com.neo.bookameetingroom.model.BookingDetails;
import com.neo.bookameetingroom.model.Person;
import com.neo.bookameetingroom.repositories.BookingDetailsRepository;
import com.neo.bookameetingroom.services.BookingService;
import com.neo.bookameetingroom.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/user")
public class UserController {

    private final PersonService personService;

    @Autowired
    BookingService bookingService;

    public UserController(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(value="/view-booking-request/{page}", method = RequestMethod.GET)
    public ModelAndView ViewBookingRequest(@PathVariable(value = "page") int page,
                                           @RequestParam(defaultValue = "id") String sortBy){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person person = personService.findByEmail(auth.getName());
        modelAndView.addObject("bookingStatus","Booking status");
        PageRequest pageable = PageRequest.of(page - 1, 5, Sort.Direction.DESC, sortBy);
        Page<BookingDetails> bookingDetailsPage = bookingService.getPaginatedBookingDetails(person, pageable);

        int totalPages = bookingDetailsPage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.addObject("activeBookingsList", true);
        modelAndView.addObject("bookingDetails", bookingDetailsPage.getContent());
        modelAndView.addObject("username", "Welcome " + person.getFirstName() + " " + person.getLastName() + " (" + person.getEmail() + ")");
        modelAndView.addObject("temp", "1");
        modelAndView.addObject("role", person.getRole().getRole());
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
        bookingService.deleteById(id);
        modelAndView.setViewName("redirect:/user/view-booking-request/1");
        return modelAndView;
    }

    @RequestMapping("/user-profile")
    public String userProfile(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person person = personService.findByEmail(auth.getName());
        model.addAttribute("role", person.getRole().getRole());
        model.addAttribute("user", person);
        return "user/user-profile";
    }

    @RequestMapping("/edit-profile")
    public String editProfile( Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person person = personService.findByEmail(auth.getName());
        model.addAttribute("role", person.getRole().getRole());
        model.addAttribute("user", person);
        return "user/edit-profile";
    }

    @PutMapping("/edit-profile")
    public String saveProfile(Person person){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person user = personService.findByEmail(auth.getName());
        user.setFirstName(person.getFirstName());
        user.setLastName(person.getLastName());
        personService.save(user);
        return "redirect:/user/user-profile";
    }
}

package com.neo.bookameetingroom.controllers;

import com.neo.bookameetingroom.model.BookingDetails;
import com.neo.bookameetingroom.model.MeetingRoom;
import com.neo.bookameetingroom.model.Person;
import com.neo.bookameetingroom.services.BookingService;
import com.neo.bookameetingroom.services.MeetingRoomService;
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
public class MeetingRoomController {

    @Autowired
    private MeetingRoomService meetingRoomService;

    @Autowired
    private PersonService personService;

    @Autowired
    private BookingService bookingService;

    @RequestMapping("/room-management/{page}")
    public String roomManagement(Model model, @PathVariable(value = "page") int page,
                                 @RequestParam(defaultValue = "id") String sortBy){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person person = personService.findByEmail(auth.getName());
        model.addAttribute("username", "Welcome " + person.getFirstName() + " " + person.getLastName() + " (" + person.getEmail() + ")");
        LocalDate today = LocalDate.now();
        for (MeetingRoom meetingRoom:meetingRoomService.findAll()) {
            for (BookingDetails bookingDetail: meetingRoom.getBookingDetails()) {
                if(bookingDetail.getDate().isEqual(today) && bookingDetail.getStatus().equals("confirmed")){
                    meetingRoom.setStatus("occupied");
                }
                else meetingRoom.setStatus("available");
            }
            meetingRoomService.save(meetingRoom);
        }
        model.addAttribute("role", person.getRole().getRole());
        PageRequest pageable = PageRequest.of(page - 1, 5, Sort.Direction.DESC, sortBy);
        Page<MeetingRoom> meetingRoomPage = meetingRoomService.getPaginatedMeetingRooms(pageable);
        int totalPages = meetingRoomPage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("activeRoomsList", true);
        model.addAttribute("meetingRooms", meetingRoomPage.getContent());
        return "meeting-room/room-management";
    }

    @RequestMapping(value = "/book-a-meeting-room/{room_id}", method = RequestMethod.GET)
    public String bookAMeetingRoom(@PathVariable("room_id") Long id, Model model){
        model.addAttribute("bookingDetails", new BookingDetails());
        model.addAttribute("room_id", id);
        return "meeting-room/book-a-meeting-room";
    }

    @PostMapping(value = "/book-a-meeting-room/{room_id}")
    public String saveBookingDetails(@PathVariable("room_id") Long id, @Valid BookingDetails bookingDetails){
        MeetingRoom meetingRoom = meetingRoomService.findById(id).orElse(null);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person person = personService.findByEmail(auth.getName());
        bookingDetails.setPerson(person);
        bookingDetails.setMeetingRoom(meetingRoom);
        bookingDetails.setStatus("pending");
        bookingService.save(bookingDetails);
        return "meeting-room/room-management";
    }

    //==================================================================================================================

    @RequestMapping("/admin/room-allocation/pending")
    public String roomAllocationPending(Model model){
        List<BookingDetails> bookingDetails = new ArrayList<>();
        for (BookingDetails bookingDetail: bookingService.findAll()) {
            if(bookingDetail.getStatus().equals("pending")){
                bookingDetails.add(bookingDetail);
            }
        }
        model.addAttribute("bookingDetails", bookingDetails);
        return "admin/booking-requests";
    }

    @RequestMapping("/admin/room-allocation/confirmed")
    public String roomAllocationConfirmed(Model model){
        List<BookingDetails> bookingDetails = new ArrayList<>();
        for (BookingDetails bookingDetail: bookingService.findAll()) {
            if(bookingDetail.getStatus().equals("confirmed")){
                bookingDetails.add(bookingDetail);
            }
        }
        model.addAttribute("bookingDetails", bookingDetails);
        return "admin/booking-requests";
    }

    @RequestMapping("/admin/room-allocation/cancelled")
    public String roomAllocationCancelled(Model model){
        List<BookingDetails> bookingDetails = new ArrayList<>();
        for (BookingDetails bookingDetail: bookingService.findAll()) {
            if(bookingDetail.getStatus().equals("cancelled")){
                bookingDetails.add(bookingDetail);
            }
        }
        model.addAttribute("bookingDetails", bookingDetails);
        return "admin/booking-requests";
    }

    @RequestMapping("/admin/room-allocation/{page}")
    public ModelAndView bookingRequests(@PathVariable(value = "page") int page,
                                        @RequestParam(defaultValue = "id") String sortBy){
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person person = personService.findByEmail(auth.getName());
        modelAndView.addObject("username", "Welcome " + person.getFirstName() + " " + person.getLastName() + " (" + person.getEmail() + ")");        PageRequest pageable = PageRequest.of(page - 1, 5, Sort.Direction.DESC, sortBy);
        Page<BookingDetails> bookingPage = bookingService.getPaginatedBookingDetails(pageable);
        int totalPages = bookingPage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.addObject("activeBookingList", true);
        modelAndView.addObject("bookingDetails", bookingPage.getContent());
        modelAndView.setViewName("admin/booking-requests");
        return modelAndView;
    }
    //==================================================================================================================

    @RequestMapping("/confirm-booking/{book_id}")
    public String confirmBooking(@PathVariable("book_id") Long book_id){
        BookingDetails bookingDetails = bookingService.findById(book_id).orElse(null);
        MeetingRoom meetingRoom = meetingRoomService.findById(bookingDetails.getMeetingRoom().getId()).orElse(null);
        meetingRoom.setStatus("occupied");
        bookingDetails.setStatus("confirmed");
        meetingRoomService.save(meetingRoom);
        bookingService.save(bookingDetails);
        return "admin/booking-requests";
    }

    @RequestMapping("/cancel-booking/{book_id}")
    public String cancelBooking(@PathVariable("book_id") Long book_id){
        BookingDetails bookingDetails = bookingService.findById(book_id).orElse(null);
        bookingDetails.setStatus("cancelled");
        bookingService.save(bookingDetails);
        return "admin/booking-requests";
    }
}
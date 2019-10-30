package com.neo.bookameetingroom.controllers;

import com.neo.bookameetingroom.model.BookingDetails;
import com.neo.bookameetingroom.model.MeetingRoom;
import com.neo.bookameetingroom.model.Person;
import com.neo.bookameetingroom.repositories.BookingDetailsRepository;
import com.neo.bookameetingroom.services.MeetingRoomService;
import com.neo.bookameetingroom.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
public class MeetingRoomController {

    @Autowired
    private MeetingRoomService meetingRoomService;

    @Autowired
    private PersonService personService;

    @Autowired
    BookingDetailsRepository bookingDetailsRepository;

    @RequestMapping("/room-management/{user_id}")
    public String roomManagement(@PathVariable("user_id") Long id, Model model){
        Person person = personService.findById(id).orElse(null);
        model.addAttribute("role", person.getRole().getRole());
        model.addAttribute("user_id", id);
        model.addAttribute("meetingRooms", meetingRoomService.findAll());
        return "meeting-room/room-management";
    }

    @RequestMapping(value = "/book-a-meeting-room/{room_id}/{user_id}", method = RequestMethod.GET)
    public String bookAMeetingRoom(@PathVariable("room_id") Long id, @PathVariable("user_id") Long user_id, Model model){
        model.addAttribute("bookingDetails", new BookingDetails());
        model.addAttribute("room_id", id);
        model.addAttribute("user_id", user_id);
        return "meeting-room/book-a-meeting-room";
    }

    @PostMapping(value = "/book-a-meeting-room/{room_id}/{user_id}")
    public String saveBookingDetails(@PathVariable("room_id") Long id, @PathVariable("user_id") Long user_id, @Valid BookingDetails bookingDetails){
        MeetingRoom meetingRoom = meetingRoomService.findById(id).orElse(null);
        Person person = personService.findById(user_id).orElse(null);
        bookingDetails.setPerson(person);
        bookingDetails.setMeetingRoom(meetingRoom);
        bookingDetails.setStatus("pending");
        bookingDetailsRepository.save(bookingDetails);
        return "meeting-room/room-management";
    }

    @RequestMapping("/admin/room-allocation")
    public String roomAllocation(Model model){
//        List<BookingDetails> bookingDetails = bookingDetailsRepository.findAll();
        model.addAttribute("bookingDetails", bookingDetailsRepository.findAll());
        return "admin/booking-requests";
    }

    @RequestMapping("/confirm-booking/{book_id}")
    public String confirmBooking(@PathVariable("book_id") Long book_id){
        BookingDetails bookingDetails = bookingDetailsRepository.findById(book_id).orElse(null);
        MeetingRoom meetingRoom = meetingRoomService.findById(bookingDetails.getMeetingRoom().getId()).orElse(null);
        meetingRoom.setStatus("occupied");
        bookingDetails.setStatus("confirmed");
        meetingRoomService.save(meetingRoom);
        bookingDetailsRepository.save(bookingDetails);
        return "admin/booking-requests";
    }

    @RequestMapping("/cancel-booking/{book_id}")
    public String cancelBooking(@PathVariable("book_id") Long book_id){
        BookingDetails bookingDetails = bookingDetailsRepository.findById(book_id).orElse(null);
        bookingDetails.setStatus("cancelled");
        bookingDetailsRepository.save(bookingDetails);
        return "admin/booking-requests";
    }
}

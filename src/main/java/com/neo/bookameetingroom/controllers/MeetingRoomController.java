package com.neo.bookameetingroom.controllers;

import com.neo.bookameetingroom.model.BookingDetails;
import com.neo.bookameetingroom.model.MeetingRoom;
import com.neo.bookameetingroom.repositories.BookingDetailsRepository;
import com.neo.bookameetingroom.services.MeetingRoomService;
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
    BookingDetailsRepository bookingDetailsRepository;

    @RequestMapping("/room-management")
    public String roomManagement(Model model){
        model.addAttribute("meetingRooms", meetingRoomService.findAll());
        return "meeting-room/room-management";
    }

    @RequestMapping(value = "/book-a-meeting-room/{id}", method = RequestMethod.GET)
    public String bookAMeetingRoom(@PathVariable Long id, Model model){
        model.addAttribute("bookingDetails", new BookingDetails());
        model.addAttribute("id", id);
        return "meeting-room/book-a-meeting-room";
    }

    @PostMapping(value = "/book-a-meeting-room/{id}")
    public String saveBookingDetails(@PathVariable Long id, @Valid BookingDetails bookingDetails){
        MeetingRoom meetingRoom = meetingRoomService.findById(id).orElse(null);
        bookingDetails.setMeetingRoom(meetingRoom);
        bookingDetailsRepository.save(bookingDetails);
        return "meeting-room/room-management";
    }

    @RequestMapping("/admin/room-allocation")
    public String roomAllocation(Model model){
//        List<BookingDetails> bookingDetails = bookingDetailsRepository.findAll();
        model.addAttribute("bookingDetails", bookingDetailsRepository.findAll());
        return "admin/booking-requests";
    }
}

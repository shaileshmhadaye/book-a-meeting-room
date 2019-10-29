package com.neo.bookameetingroom.controllers;

import com.neo.bookameetingroom.model.Facility;
import com.neo.bookameetingroom.model.MeetingRoom;
import com.neo.bookameetingroom.model.Person;
import com.neo.bookameetingroom.model.Role;
import com.neo.bookameetingroom.repositories.FacilityRepository;
import com.neo.bookameetingroom.repositories.RoleRepository;
import com.neo.bookameetingroom.services.MeetingRoomService;
import com.neo.bookameetingroom.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PersonService personService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MeetingRoomService meetingRoomService;

    @Autowired
    private FacilityRepository facilityRepository;

    @RequestMapping(value="/home", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person user = personService.findByEmail(auth.getName());
        modelAndView.addObject("username", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

    @RequestMapping(value="/console", method = RequestMethod.GET)
    public ModelAndView console(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person user = personService.findByEmail(auth.getName());
        modelAndView.addObject("username", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("users", personService.findAll());
        modelAndView.setViewName("admin/console");
        return modelAndView;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model){
        Person person = personService.findById(id).orElse(null);
        model.addAttribute("person", person);
        model.addAttribute("id", id);
        return "admin/edit";
    }

    @PutMapping(value = "/editSave/{id}")
    public String editSave(@PathVariable Long id, @RequestParam(name = "role") Long role, @Valid @ModelAttribute("person") Person person){
        Role role1 = roleRepository.findById(role).orElse(null);
        person.setRole(role1);
        personService.update(person);
        return "admin/console";
    }

    @RequestMapping(value = "/delete/{id}")
    public String delete(@PathVariable Long id){
        personService.deleteById(id);
        return "admin/console";
    }

    @RequestMapping(value = "/addMeetingRoom")
    public String addMeetingRoom(Model model){
        model.addAttribute("facilities", facilityRepository.findAll());
        model.addAttribute("meetingRoom", new MeetingRoom());
        return "meeting-room/add-a-meeting-room";
    }

    @PostMapping(value = "/saveMeetingRoom")
    public String saveMeetingRoom(@ModelAttribute("meetingRoom") MeetingRoom meetingRoom,
                                  @RequestParam("items") Long[] items){
        if(items!=null){
            List<Facility> facilities = new ArrayList<>();
            Facility facility;
            for (int i = 0; i<items.length; i++){
                facility = facilityRepository.findById(items[i]).orElse(null);
                if(facility != null){
                    facilities.add(facility);
                }
            }
            meetingRoom.setFacilities(facilities);
        }
        meetingRoomService.save(meetingRoom);
        return "meeting-room/room-management";
    }

    @RequestMapping("/room-management")
    public String roomManagement(Model model){
        model.addAttribute("meetingRooms", meetingRoomService.findAll());
        return "meeting-room/room-management";
    }

    @RequestMapping("/book-a-meeting-room/{id}")
    public String bookAMeetingRoom(@PathVariable Long id, Model model){
        model.addAttribute("id", id);
        return "meeting-room/book-a-meeting-room";
    }
}

package com.neo.bookameetingroom.controllers;

import com.neo.bookameetingroom.model.*;
import com.neo.bookameetingroom.repositories.ChangeInfoRequestRepository;
import com.neo.bookameetingroom.repositories.FacilityRepository;
import com.neo.bookameetingroom.repositories.RoleRepository;
import com.neo.bookameetingroom.services.MeetingRoomService;
import com.neo.bookameetingroom.services.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Slf4j
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

    @Autowired
    private ChangeInfoRequestRepository changeInfoRequestRepository;

    @RequestMapping(value="/console/{page}", method = RequestMethod.GET)
    public ModelAndView console(@PathVariable(value = "page") int page,
                                @RequestParam(defaultValue = "id") String sortBy){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person user = personService.findByEmail(auth.getName());

        PageRequest pageable = PageRequest.of(page - 1, 5, Sort.Direction.DESC, sortBy);
        Page<Person> userPage = personService.getPaginatedUsers(pageable);
        int totalPages = userPage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.addObject("activeUserList", true);
        modelAndView.addObject("users", userPage.getContent());

        modelAndView.addObject("username", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
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
        return "redirect:/admin/console/1";
    }

    @RequestMapping(value = "/delete/{id}")
    public String delete(@PathVariable Long id){
        personService.deleteById(id);
        return "redirect:/admin/console/1";
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
        meetingRoom.setStatus("available");
        meetingRoomService.save(meetingRoom);
        return "redirect:/meeting-room/room-management";
    }

    @RequestMapping("/update-meeting-room/{room_id}")
    public ModelAndView updateRoom(@PathVariable("room_id") Long id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("facilities", facilityRepository.findAll());
        MeetingRoom meetingRoom = meetingRoomService.findById(id).orElse(null);
        modelAndView.addObject("meetingRoom", meetingRoom);
        modelAndView.addObject("id", id);
        modelAndView.setViewName("admin/update-meeting-room");
        return modelAndView;
    }

    @RequestMapping(value = "/update-meeting-room/{room_id}", method = RequestMethod.PUT)
    public ModelAndView saveRoom(@PathVariable(value="room_id") Long id,
                                 @Valid @ModelAttribute("meetingRoom") MeetingRoom meetingRoomData,
                                 @RequestParam("items") Long[] items) {
        MeetingRoom meetingRoom = meetingRoomService.findById(id).orElse(null);
        ModelAndView modelAndView = new ModelAndView();
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
        meetingRoom.setDescription(meetingRoomData.getDescription());
        meetingRoom.setLocation(meetingRoomData.getLocation());
        meetingRoomService.save(meetingRoom);
        modelAndView.addObject("successMessage", "MeetingRoom has been Updated successfully");
        modelAndView.addObject("meetingRoom", meetingRoomData);
        modelAndView.setViewName("redirect:/room-management/1");
        return modelAndView;
    }

    @RequestMapping("/delete-meeting-room/{room_id}")
    public ModelAndView deleteRoom(@PathVariable("room_id") Long id){
        ModelAndView modelAndView = new ModelAndView();
        meetingRoomService.deleteById(id);
        modelAndView.setViewName("redirect:/room-management/1");
        return modelAndView;
    }

    @RequestMapping(value = "/add-user", method = RequestMethod.GET)
    public ModelAndView register(){
        ModelAndView modelAndView = new ModelAndView();
        Person person = new Person();
        modelAndView.addObject("person", person);
        modelAndView.setViewName("admin/add-user");
        return modelAndView;
    }

    @RequestMapping(value = "/add-user", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid Person person, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        Person person1 = personService.findByEmail(person.getEmail());
        if (person1 != null){
            bindingResult
                    .rejectValue("email", "error.person",
                            "There is already a user registered with the email provided");

        }
        if (bindingResult.hasErrors()){
            modelAndView.setViewName("admin/add-user");
        }else {
            personService.save(person);
            modelAndView.setViewName("redirect:/admin/console/1");
        }
        return modelAndView;
    }

    @RequestMapping("/user-info-change-requests")
    public ModelAndView changeRequests(ModelAndView modelAndView){
        modelAndView.addObject("changeRequests", changeInfoRequestRepository.findAll());
        modelAndView.setViewName("admin/user-info-change-requests");
        return modelAndView;
    }

    @PostMapping("/edit-with-request/{request_id}")
    public String  editUser(@PathVariable(value = "request_id") Long id){
        ChangeInfoRequest changeInfoRequest = changeInfoRequestRepository.findById(id).orElse(null);
        Person person = personService.findById(changeInfoRequest.getPerson().getId()).orElse(null);
        if(changeInfoRequest.getType().equals("email")){
            person.setEmail(changeInfoRequest.getNewOne());
        }else{
            person.setDepartment(changeInfoRequest.getNewOne());
        }
        changeInfoRequest.setStatus("confirmed");
        changeInfoRequestRepository.save(changeInfoRequest);
        personService.update(person);
        return "redirect:/admin/user-info-change-requests";
    }

    @PostMapping("/reject-with-request/{request_id}")
    public ModelAndView rejectRequest(ModelAndView modelAndView, @PathVariable("request_id") Long id){
        ChangeInfoRequest changeInfoRequest = changeInfoRequestRepository.findById(id).orElse(null);
        changeInfoRequest.setStatus("rejected");
        changeInfoRequestRepository.save(changeInfoRequest);
        modelAndView.setViewName("redirect:/admin/user-info-change-requests");
        return modelAndView;
    }
}

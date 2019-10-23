package com.neo.bookameetingroom.controllers;

import com.neo.bookameetingroom.model.Person;
import com.neo.bookameetingroom.model.Role;
import com.neo.bookameetingroom.repositories.RoleRepository;
import com.neo.bookameetingroom.services.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
public class IndexController {

    @Autowired
    private PersonService personService;

    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register(){
        ModelAndView modelAndView = new ModelAndView();
        Person person = new Person();
        modelAndView.addObject("person", person);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid Person person, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        Person person1 = personService.findByEmail(person.getEmail());
        if (person1 != null){
            bindingResult
                    .rejectValue("email", "error.person",
                            "There is already a user registered with the email provided");

        }
        if (bindingResult.hasErrors()){
            modelAndView.setViewName("register");
        }else {
            personService.save(person);
            modelAndView.addObject("successMessage", "User has been registered successfully!");
            modelAndView.addObject("person", new Person());
            modelAndView.setViewName("register");
        }
        return modelAndView;
    }

    @RequestMapping(value="/admin/home", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person user = personService.findByEmail(auth.getName());
        modelAndView.addObject("username", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.addObject("users", personService.findAll());
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model){
        Person person = personService.findById(id).orElse(null);
        model.addAttribute("person", person);
        model.addAttribute("id", id);
        return "admin/edit";
    }

    @PutMapping(value = "/admin/editSave/{id}")
    public String editSave(@PathVariable Long id, @RequestParam(name = "role") String role, @Valid @ModelAttribute("person") Person person){
        Person person2 = personService.findById(id).orElse(null);

        Role role1 = roleRepository.findByRole(role);

        person.setId(id);
        person.setPassword(person2.getPassword());
        person.setActive(person2.getActive());
        person.setRole(role1);
        personService.save(person);
        return "admin/home";
    }

    @RequestMapping(value = "/admin/delete/{id}")
    public String delete(@PathVariable Long id){
        personService.deleteById(id);
        return "admin/home";
    }

    @RequestMapping(value = "access-denied")
    public String accessDenied(){
        return "access-denied";
    }

    @RequestMapping(value = "user/home")
    public String userHome(){
        return "user/home";
    }

}

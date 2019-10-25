package com.neo.bookameetingroom.controllers;

import com.neo.bookameetingroom.model.Person;
import com.neo.bookameetingroom.model.Role;
import com.neo.bookameetingroom.repositories.RoleRepository;
import com.neo.bookameetingroom.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PersonService personService;

    @Autowired
    private RoleRepository roleRepository;

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
}

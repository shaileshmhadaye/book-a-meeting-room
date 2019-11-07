package com.neo.bookameetingroom.controllers;

import com.neo.bookameetingroom.model.Person;
import com.neo.bookameetingroom.model.Token;
import com.neo.bookameetingroom.repositories.TokenRepository;
import com.neo.bookameetingroom.services.EmailService;
import com.neo.bookameetingroom.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class PasswordResetController {

    @Autowired
    private PersonService personService;

    @Autowired
    private TokenRepository tokenService;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/forgot-password", method = RequestMethod.GET)
    public ModelAndView displayForgotPasswordPage() {
        return new ModelAndView("forgot-password");
    }

    @PostMapping(value = "/forgot-password")
    public ModelAndView forgotPassword(ModelAndView modelAndView, @RequestParam("email") String email, HttpServletRequest request){

        Person person = personService.findByEmail(email);

        if(person == null){
            modelAndView.addObject("errorMessage", "We didn't find an account for that e-mail address.");
        }else {
            Token token = new Token(person);

            tokenService.save(token);
            emailService.sendEmail(email, token.getToken());
            modelAndView.addObject("successMessage", "A password reset link has been sent to " + email);
        }
        modelAndView.setViewName("/login");
        return modelAndView;
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.GET)
    public ModelAndView resetPassword(ModelAndView modelAndView, @RequestParam("token") String token){
        Token token1 = tokenService.findByToken(token);
        if(token1 != null){
            Person person = token1.getPerson();
            modelAndView.addObject("user", person);
        }else {
            modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
        }
        modelAndView.setViewName("reset-password");
        return modelAndView;
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public ModelAndView resetPasswordConfirmed(ModelAndView modelAndView, Person person){
        if(person.getEmail() != null){
            Person user = personService.findByEmail(person.getEmail());
            user.setPassword(person.getPassword());
            personService.update(user);
            modelAndView.addObject("message",
                    "Password successfully reset. You can now log in with the new credentials.");
            modelAndView.setViewName("/login");
        }else {
            modelAndView.addObject("message", "The link is invalid or broken!");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

}

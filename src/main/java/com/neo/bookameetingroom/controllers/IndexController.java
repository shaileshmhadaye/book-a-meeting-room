package com.neo.bookameetingroom.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping({"", "/", "/login"})
    public String index(){
        return "login";
    }

    @RequestMapping("/home")
    public String home(){
        return "dashboard";
    }
}

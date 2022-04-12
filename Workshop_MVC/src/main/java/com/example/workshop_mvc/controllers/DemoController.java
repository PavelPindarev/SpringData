package com.example.workshop_mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DemoController {

    @GetMapping("/home")
    public String getView() {
        return "home";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/users/register")
    public ModelAndView register(){
        ModelAndView registerView = new ModelAndView("user/register");
        return registerView;
    }

    @GetMapping("/users/login")
    public String login(){
        return "user/login";
    }

    @GetMapping("/import/xml")
    public String getImportView(){
        return "xml/import-xml";
    }
}

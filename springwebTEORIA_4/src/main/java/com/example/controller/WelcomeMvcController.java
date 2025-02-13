package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.service.WelcomeService;

@Controller
@RequestMapping("/test")
public class WelcomeMvcController {
    
	@Autowired
    private WelcomeService welcomeService;
    
    @GetMapping(value = "/saluto")
    public String greeting1(String name, Model model) {
        model.addAttribute("welcome", welcomeService.greetingMessage1(name));
        return "welcome-page";
    }
    
    @GetMapping(value = "/event")
    public String greeting2(String name, Model model) {
        model.addAttribute("welcomeToEvent", welcomeService.greetingMessage2(name));
        return "event-page";
    }
}

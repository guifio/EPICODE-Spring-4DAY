package com.example.service;

import org.springframework.stereotype.Service;

@Service
public class WelcomeService {
    public String greetingMessage1(String name) {
        return String.format("Welcome , %s to the world of programming!!!", name );
    }
  
    public String greetingMessage2(String attendee) {
        return String.format("Welldone , %s You are selected to the contest!!!", attendee );
    }
}

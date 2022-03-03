package com.grupo5.huiapi.entities.event;

import com.fasterxml.jackson.databind.JsonNode;
import com.grupo5.huiapi.exceptions.CategoryNotFoundException;
import com.grupo5.huiapi.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    public List<Event> getEvents() { return eventService.getEvents();}

    @PostMapping
    public String createNewEvent(@RequestBody JsonNode jsonEvent) {
        System.out.println(jsonEvent);
        try {
            return eventService.insertEvent(jsonEvent);
        } catch (CategoryNotFoundException | UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }


}

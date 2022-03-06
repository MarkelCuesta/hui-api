package com.grupo5.huiapi.entities.event;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.huiapi.entities.category.Category;
import com.grupo5.huiapi.entities.category.CategoryService;
import com.grupo5.huiapi.entities.user.User;
import com.grupo5.huiapi.entities.user.UserService;
import com.grupo5.huiapi.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

    public String insertEvent(JsonNode eventNode) throws CategoryNotFoundException, UserNotFoundException {
        Set<Category> categories = categoryService.getCategories(eventNode);
        String title = eventNode.get("title").asText();
        String description = eventNode.get("description").asText();
        User user = userService.getUser( eventNode.get("organizer").asLong() );
        Event event = new Event(title, description, categories, user);
        eventRepository.save(event);
        return "Event registered";
    }


    public String updateEvent(Long id, String password, JsonNode event) throws EventNotFoundException, CategoryNotFoundException, UserNotFoundException, IncorrectPasswordException, RequiredValuesMissingException {
        // Event id exists
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty())
            throw new EventNotFoundException();

        // User id exists
        Long organizerId = event.get("organizer").asLong();
        User user = userService.getUser(organizerId);
        if( !user.getPassword().equals(password) )
            throw new IncorrectPasswordException();

        // Categories exist
        Set<Category> categories = categoryService.getCategories(event.get("categories"));
        String title = event.get("title").asText();
        String description  = event.get("description").asText();

        // Non missing required values
        Event updatingEvent = new Event(title, description, categories, user);
        String nullFields = updatingEvent.checkNullFields();
        if(nullFields != null)
            throw new RequiredValuesMissingException(nullFields);

        updatingEvent.setId(id);
        eventRepository.save(updatingEvent);
        return "Event updated";
    }

    public Event getEvent(Long id) throws EventNotFoundException {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if(optionalEvent.isEmpty())
            throw new EventNotFoundException();
        return optionalEvent.get();
    }

    public String deleteEvent(Long id, String password) throws EventNotFoundException {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if(optionalEvent.isEmpty())
            throw new EventNotFoundException();

        eventRepository.delete(optionalEvent.get());
        return "Event removed";
    }
}

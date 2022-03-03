package com.grupo5.huiapi.entities.event;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.huiapi.entities.category.Category;
import com.grupo5.huiapi.entities.category.CategoryService;
import com.grupo5.huiapi.entities.user.User;
import com.grupo5.huiapi.entities.user.UserService;
import com.grupo5.huiapi.exceptions.CategoryNotFoundException;
import com.grupo5.huiapi.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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


}

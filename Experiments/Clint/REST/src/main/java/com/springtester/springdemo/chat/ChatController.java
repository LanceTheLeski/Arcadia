package com.springtester.springdemo.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@EnableJpaRepositories
@RestController
class ChatController {

    @Autowired
    public ChatRepository messagesRepository;

    @PostMapping("/message/new")
    public String saveMessage(@RequestBody Chat message) {
        messagesRepository.save(message);
        return "New message '"+ message.getMessage() + "' Saved under user '" + message.getUser() + "' at " + message.getDate();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/messages")
    public List<Chat> getAllMessages() {
        List<Chat> results = messagesRepository.findAll();
        return results;
    }

}

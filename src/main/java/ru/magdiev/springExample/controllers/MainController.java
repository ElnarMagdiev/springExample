package ru.magdiev.springExample.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.magdiev.springExample.entity.Message;
import ru.magdiev.springExample.entity.User;
import ru.magdiev.springExample.repositories.MessageRepository;


@Controller
public class MainController {

    private MessageRepository messageRepository;

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Message> messages =  messageRepository.findAll();
        if (filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        }else {
            messages = messageRepository.findAll();
        }
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "main";
    }

    @PostMapping("/main")
    public String add(@AuthenticationPrincipal User user,
                      @RequestParam String text,
                      @RequestParam String tag,
                      Model model) {
        Message message = new Message(text, tag, user);
        messageRepository.save(message);
        Iterable<Message> messages =  messageRepository.findAll();
        model.addAttribute("messages", messages);

        return "main";
    }



    @Autowired
    public void setMessageRepository(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
}

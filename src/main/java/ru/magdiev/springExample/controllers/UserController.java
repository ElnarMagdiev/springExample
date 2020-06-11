package ru.magdiev.springExample.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.magdiev.springExample.entity.User;
import ru.magdiev.springExample.repositories.UserRepository;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String userList(Model model){
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "userList";
    }
}

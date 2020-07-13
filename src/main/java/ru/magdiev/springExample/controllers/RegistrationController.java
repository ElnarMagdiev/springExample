package ru.magdiev.springExample.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;
import ru.magdiev.springExample.entity.User;
import ru.magdiev.springExample.service.UserService;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {

    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("passwordConf") String passwordConf,
            @Valid User user,
            BindingResult bindingResult,
            Model model) {

        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConf);
        if (isConfirmEmpty) {
            model.addAttribute("passwordConfError", "Конфигурация пароля не может быть пустым");
        }
        if (user.getPassword() != null && !user.getPassword().equals(passwordConf)) {
            model.addAttribute("passwordError", "Пароли не совпадают!");
        }
        if (isConfirmEmpty || bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "registration";
        }

        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "Такой пользователь уже зарегестрирован");
            return "registration";
        }

        return "redirect:/login";
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code is not found");
        }
        return "login";
    }
}

package fr.limayrac.ProjetSpring.controller;

import fr.limayrac.ProjetSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/home")
    public String home() {
        return "adminHome";
    }

    @GetMapping("/admin/createUser")
    public String createUserForm() {
        return "createUser";
    }

    @PostMapping("/admin/saveUser")
    public String saveUser(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("role") String role) {
        userService.saveUser(username, password, role);
        return "redirect:/admin/home";
    }
}
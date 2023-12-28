package fr.limayrac.ProjetSpring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Ajoutez d'autres méthodes de contrôleur ici au besoin
}
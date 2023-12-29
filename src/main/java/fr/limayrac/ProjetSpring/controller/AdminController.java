package fr.limayrac.ProjetSpring.controller;

import fr.limayrac.ProjetSpring.model.Declaration;
import fr.limayrac.ProjetSpring.repository.DeclarationRepository;
import fr.limayrac.ProjetSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private DeclarationRepository declarationRepository;

    @GetMapping("/admin/home")
    public String home(Model model) {
        List<Declaration> declarations = declarationRepository.findByStatusIn(Arrays.asList("attente", "valide", "invalide"));
        model.addAttribute("declarations", declarations);
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

    @PostMapping("/admin/validateDeclaration")
    public String validateDeclaration(@RequestParam("declarationId") Long declarationId, @RequestParam("action") String action) {
        Declaration declaration = declarationRepository.findById(declarationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid declaration ID: " + declarationId));
        declaration.setStatus(action); // action devrait être "valide" ou "invalide"
        declarationRepository.save(declaration);
        return "redirect:/admin/home";
    }
}
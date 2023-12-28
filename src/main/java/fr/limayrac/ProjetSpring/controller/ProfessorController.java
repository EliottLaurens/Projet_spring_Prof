package fr.limayrac.ProjetSpring.controller;

import fr.limayrac.ProjetSpring.model.DeclarationForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfessorController {

    @GetMapping("/professor/home")
    public String home() {
        return "professorHome";
    }

    @GetMapping("/professor/prepareDeclaration")
    public String prepareDeclaration(Model model) {
        return "declarationForm";
    }

    @PostMapping("/professor/submitDeclaration")
    public String submitDeclaration(@ModelAttribute DeclarationForm declarationForm) {
        // Traitez les données du formulaire de déclaration ici
        // Par exemple, enregistrer les informations dans la base de données

        return "redirect:/professor/home"; // Rediriger vers la page d'accueil du professeur après la soumission
    }
}
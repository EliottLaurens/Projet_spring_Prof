package fr.limayrac.ProjetSpring.controller;

import fr.limayrac.ProjetSpring.model.Declaration;
import fr.limayrac.ProjetSpring.repository.DeclarationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class ProfessorController {

    @Autowired
    private DeclarationRepository declarationRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProfessorController.class);


    @GetMapping("/professor/home")
    public String home(Model model) {
        List<Declaration> declarations = declarationRepository.findByStatusIn(Arrays.asList("attente", "valide", "invalide"));
        model.addAttribute("declarations", declarations);
        return "professorHome";
    }

    @GetMapping("/professor/editDeclaration")
    public String editDeclaration(@RequestParam("declarationId") Long declarationId) {

        // Redirigez vers la page de récapitulatif avec l'ID de la déclaration
        return "redirect:/professor/recapitulatif?declarationId=" + declarationId;
    }


    @GetMapping("/professor/prepareDeclaration")
    public String prepareDeclaration(Model model) {
        model.addAttribute("stepNumber", 1);
        return "declaration/prepareDeclaration";
    }

    @GetMapping("/professor/detailsFormation")
    public String detailsFormation(@RequestParam("declarationId") Long declarationId, Model model) {
        model.addAttribute("stepNumber", 2);
        Declaration declaration = declarationRepository.findById(declarationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid declaration ID: " + declarationId));
        model.addAttribute("declaration", declaration);
        return "declaration/detailsFormation";
    }


    @GetMapping("/professor/fraisTransport")
    public String fraisTransport(@RequestParam("declarationId") Long declarationId, Model model) {
        model.addAttribute("stepNumber", 3);
        Declaration declaration = declarationRepository.findById(declarationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid declaration ID: " + declarationId));
        model.addAttribute("declaration", declaration);
        return "declaration/fraisTransport";
    }

    @GetMapping("/professor/fraisHebergement")
    public String fraisHebergement(@RequestParam("declarationId") Long declarationId, Model model) {
        model.addAttribute("stepNumber", 4);
        Declaration declaration = declarationRepository.findById(declarationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid declaration ID: " + declarationId));
        model.addAttribute("declaration", declaration);
        return "declaration/fraisHebergement";
    }

    @GetMapping("/professor/fraisRestauration")
    public String fraisRestauration(@RequestParam("declarationId") Long declarationId,Model model) {
        model.addAttribute("stepNumber", 5);
        Declaration declaration = declarationRepository.findById(declarationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid declaration ID: " + declarationId));
        model.addAttribute("declaration", declaration);
        return "declaration/fraisRestauration";
    }

    @GetMapping("/professor/coordonneesBancaires")
    public String coordonneesBancaires(@RequestParam("declarationId") Long declarationId,Model model) {
        model.addAttribute("stepNumber", 6);
        Declaration declaration = declarationRepository.findById(declarationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid declaration ID: " + declarationId));
        model.addAttribute("declaration", declaration);
        return "declaration/coordonneesBancaires";
    }

    @GetMapping("/professor/recapitulatif")
    public String recapitulatif(@RequestParam("declarationId") Long declarationId,Model model) {
        model.addAttribute("stepNumber", 7);
        Declaration declaration = declarationRepository.findById(declarationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid declaration ID: " + declarationId));
        model.addAttribute("declaration", declaration);
        return "declaration/recapitulatif";
    }

    @GetMapping("/professor/newDeclaration")
    public String newDeclaration(RedirectAttributes redirectAttributes) {
        Declaration newDeclaration = new Declaration();
        newDeclaration.setDateCreation(new Date());
        newDeclaration.setStatus("En cours");
        declarationRepository.save(newDeclaration); // Sauvegarder la nouvelle déclaration
        redirectAttributes.addFlashAttribute("declaration", newDeclaration);
        return "redirect:/professor/detailsFormation?declarationId=" + newDeclaration.getId();
    }


    @PostMapping("/professor/processDetailsFormation")
    public String processDetailsFormation(@ModelAttribute Declaration formDeclaration, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "declaration/detailsFormation";
        }

        Declaration existingDeclaration = declarationRepository.findById(formDeclaration.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid declaration ID: " + formDeclaration.getId()));

        logger.info("Avant mise à jour: {}", existingDeclaration.getIntituleFormation());

        // Mettez à jour l'instance existante avec les nouvelles valeurs
        updateDeclaration(existingDeclaration, formDeclaration);

        declarationRepository.save(existingDeclaration);
        logger.info("Après mise à jour: {}", existingDeclaration.getIntituleFormation());
        redirectAttributes.addFlashAttribute("declaration", existingDeclaration);
        return "redirect:/professor/fraisTransport?declarationId=" + existingDeclaration.getId();
    }

    @PostMapping("/professor/processTransport")
    public String processTransport(@ModelAttribute Declaration formDeclaration, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "declaration/fraisTransport";
        }

        Declaration existingDeclaration = declarationRepository.findById(formDeclaration.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid declaration ID: " + formDeclaration.getId()));

        logger.info("Avant mise à jour: {}", existingDeclaration.getTransportType());

        updateDeclaration(existingDeclaration, formDeclaration);

        declarationRepository.save(existingDeclaration);
        logger.info("Après mise à jour: {}", existingDeclaration.getTransportType());
        redirectAttributes.addFlashAttribute("declaration", existingDeclaration);
        return "redirect:/professor/fraisHebergement?declarationId=" + existingDeclaration.getId();
    }

    @PostMapping("/professor/processHebergement")
    public String processHebergement(@ModelAttribute Declaration formDeclaration, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "declaration/fraisHebergement";
        }

        Declaration existingDeclaration = declarationRepository.findById(formDeclaration.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid declaration ID: " + formDeclaration.getId()));

        logger.info("Avant mise à jour hebergement: {}", existingDeclaration.isHebergementGratuit());

        updateDeclaration(existingDeclaration, formDeclaration);

        declarationRepository.save(existingDeclaration);
        logger.info("Après mise à jour hebergement: {}", existingDeclaration.isHebergementGratuit());
        redirectAttributes.addFlashAttribute("declaration", existingDeclaration);
        return "redirect:/professor/fraisRestauration?declarationId=" + existingDeclaration.getId();
    }

    @PostMapping("/professor/processRestauration")
    public String processRestauration(@ModelAttribute Declaration formDeclaration, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "declaration/fraisRestauration";
        }

        Declaration existingDeclaration = declarationRepository.findById(formDeclaration.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid declaration ID: " + formDeclaration.getId()));

        logger.info("Avant mise à jour: {}", existingDeclaration.getMontantRestauration());

        updateDeclaration(existingDeclaration, formDeclaration);

        declarationRepository.save(existingDeclaration);
        logger.info("Après mise à jour: {}", existingDeclaration.getMontantRestauration());
        redirectAttributes.addFlashAttribute("declaration", existingDeclaration);
        return "redirect:/professor/coordonneesBancaires?declarationId=" + existingDeclaration.getId();
    }

    @PostMapping("/professor/processCoordonneesBancaires")
    public String processCoordonneesBancaires(@ModelAttribute Declaration formDeclaration, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "declaration/coordonneesBancaires";
        }

        Declaration existingDeclaration = declarationRepository.findById(formDeclaration.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid declaration ID: " + formDeclaration.getId()));

        logger.info("Avant mise à jour: {}", existingDeclaration.getTitulaireCompte());

        updateDeclaration(existingDeclaration, formDeclaration);

        declarationRepository.save(existingDeclaration);
        logger.info("Après mise à jour: {}", existingDeclaration.getTitulaireCompte());
        redirectAttributes.addFlashAttribute("declaration", existingDeclaration);
        return "redirect:/professor/recapitulatif?declarationId=" + existingDeclaration.getId();
    }

    @PostMapping("/professor/validateDeclaration")
    public String validateDeclaration(@ModelAttribute Declaration formDeclaration, RedirectAttributes redirectAttributes) {
        Declaration existingDeclaration = declarationRepository.findById(formDeclaration.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid declaration ID: " + formDeclaration.getId()));

        logger.info("Avant mise à jour: {}", existingDeclaration.getStatus());

        existingDeclaration.setStatus("attente");

        declarationRepository.save(existingDeclaration);
        logger.info("Après mise à jour: {}", existingDeclaration.getStatus());
        redirectAttributes.addFlashAttribute("declaration", existingDeclaration);
        return "redirect:/professor/home";
    }

    private void updateDeclaration(Declaration existingDeclaration, Declaration formDeclaration) {
        // Détails de la formation
        if(formDeclaration.getRefDossier() != null) {
            existingDeclaration.setRefDossier(formDeclaration.getRefDossier());
        }
        if (formDeclaration.getDateFormation() != null) {
            existingDeclaration.setDateFormation(formDeclaration.getDateFormation());
        }
        if (formDeclaration.getLieuFormation() != null) {
            existingDeclaration.setLieuFormation(formDeclaration.getLieuFormation());
        }
        if (formDeclaration.getIntituleFormation() != null) {
            existingDeclaration.setIntituleFormation(formDeclaration.getIntituleFormation());
        }

        // Frais de transport
        if (formDeclaration.getTransportType() != null) {
            existingDeclaration.setTransportType(formDeclaration.getTransportType());
        }

        // Frais d'hébergement
        if (formDeclaration.isHebergementGratuit() !=null) {
            existingDeclaration.setHebergementGratuit(formDeclaration.isHebergementGratuit());
        }

        // Frais de restauration
        if (formDeclaration.isFraisRestauration() !=null) {
            existingDeclaration.setFraisRestauration(formDeclaration.isFraisRestauration());
        }
        if (formDeclaration.getMontantRestauration() != null) {
            existingDeclaration.setMontantRestauration(formDeclaration.getMontantRestauration());
        }

        // Coordonnées bancaires
        if (formDeclaration.getTitulaireCompte() != null) {
            existingDeclaration.setTitulaireCompte(formDeclaration.getTitulaireCompte());
        }
        if (formDeclaration.getIban() != null) {
            existingDeclaration.setIban(formDeclaration.getIban());
        }
        if (formDeclaration.getBic() != null) {
            existingDeclaration.setBic(formDeclaration.getBic());
        }
    }
}
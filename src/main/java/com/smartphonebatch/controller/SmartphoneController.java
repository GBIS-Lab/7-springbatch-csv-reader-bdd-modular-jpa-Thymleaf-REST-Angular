package com.smartphonebatch.controller;

import com.smartphonebatch.model.Smartphone;
import com.smartphonebatch.repository.SmartphoneRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controller
@RestController
@RequestMapping("/api/smartphones")
@CrossOrigin(origins = "*") // Pour autoriser Angular à appeler l’API
public class SmartphoneController {

    private final SmartphoneRepository smartphoneRepository;

    public SmartphoneController(SmartphoneRepository smartphoneRepository) {
        this.smartphoneRepository = smartphoneRepository;
    }

    @GetMapping("/smartphonesList")
    public String getSmartphones(Model model) {
        model.addAttribute("smartphones", smartphoneRepository.findAll());
        return "smartphonesList"; // correspond au nom du fichier HTML
        //ATTENTION :
        // ceci est sensé retourner "smartphonesList" avec la liste de getSmartphones(), qui est censé être une vue Thymeleaf.
        // Mais comme ce contrôleur est annoté avec @RestController, Spring ne va pas rendre une page HTML : il va retourner le texte "smartphonesList" dans la réponse HTTP.
    }

    @GetMapping
    public List<Smartphone> getAllSmartphones() {
        return smartphoneRepository.findAll();
    }

    // Méthode pour l'accès par ID :
    @GetMapping("/{id}")
    public ResponseEntity<Smartphone> getSmartphoneById(@PathVariable Long id) {
        return smartphoneRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

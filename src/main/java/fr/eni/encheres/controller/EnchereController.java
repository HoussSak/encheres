package fr.eni.encheres.controller;

import fr.eni.encheres.dto.response.ResponseArticleVenduDto;
import fr.eni.encheres.model.Enchere;
import fr.eni.encheres.service.impl.EnchereService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/encheres",produces = "application/vnd.api.v1+json")
@SecurityRequirement(name = "bearerAuth")
public class EnchereController {
    private final EnchereService enchereService;

    public EnchereController(EnchereService enchereService) {
        this.enchereService = enchereService;
    }

    @PostMapping("/{idArticle}")
    public void addNeweEnchere( @PathVariable Integer idArticle, @RequestParam Integer montant, Principal principal) {
        enchereService.addNewEnchere( idArticle, montant,principal);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Enchere>> getEnchereList() {
        log.info("Processing finding all encheres");
        return new ResponseEntity<> (enchereService.findAllEnchere(), HttpStatus.OK);
    }
}

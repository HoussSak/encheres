package fr.eni.encheres.controller;

import fr.eni.encheres.model.Categorie;
import fr.eni.encheres.repository.CategoryRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/categories", produces = "application/vnd.api.v1+json")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Categorie>> findAllCategories() {
        log.info("processing finding all categories");
        List<Categorie> all = categoryRepository.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK) ;
    }

    @PostMapping("/add")
    public ResponseEntity<Categorie> addCategory(@RequestBody @Valid Categorie categorie) {
        log.info("Creating new category for with email");
        Categorie savedCategory = categoryRepository.save(categorie);
        return new ResponseEntity<>(savedCategory, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{idCategory}")
    public void deleteCategoryByAdmin(@PathVariable("idCategory") Integer id) {
        categoryRepository.deleteById(id);
    }
}

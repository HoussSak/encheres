package fr.eni.encheres.controller;

import fr.eni.encheres.dto.create.CreateArticleVenduDto;
import fr.eni.encheres.dto.response.ResponseArticleVenduDto;
import fr.eni.encheres.exception.ErrorCodes;
import fr.eni.encheres.exception.InvalidEntityException;
import fr.eni.encheres.model.Enchere;
import fr.eni.encheres.model.EnchereId;
import fr.eni.encheres.service.ArticleVenduService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/articles", produces = "application/vnd.api.v1+json")
@SecurityRequirement(name = "bearerAuth")
public class ArticleVenduController {

    private final ArticleVenduService articleVenduService;

    public ArticleVenduController(ArticleVenduService articleVenduService) {
        this.articleVenduService = articleVenduService;
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseArticleVenduDto> addArticle(@RequestBody CreateArticleVenduDto articleVenduDto, Principal principal, BindingResult result) {
        List<String> errors = result.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        if (!errors.isEmpty()) {
            throw  new InvalidEntityException("Article not valid not valid", ErrorCodes.ARTICLE_NOT_VALID,errors);
        }
        log.info("Creating new article: {}", articleVenduDto.getNomArticle());
        ResponseArticleVenduDto savedArticle = articleVenduService.saveArticle(articleVenduDto, principal);
        return new ResponseEntity<>(savedArticle, HttpStatus.OK);
    }
    @PatchMapping("/update/{idArticle}")
    public ResponseEntity<ResponseArticleVenduDto> UpdateArticle(@RequestBody CreateArticleVenduDto articleVenduDto,@PathVariable("idArticle") Integer id,
                                                                 BindingResult result) {
        List<String> errors = result.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        if (!errors.isEmpty()) {
            throw  new InvalidEntityException("Article not valid not valid", ErrorCodes.ARTICLE_NOT_VALID,errors);
        }
        log.info("Updating article with id: {}", id);
        ResponseArticleVenduDto savedArticle = articleVenduService.updateArticle(articleVenduDto, id);
        return new ResponseEntity<>(savedArticle, HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<ResponseArticleVenduDto>> getArticleList() {
        log.info("Processing finding all articles");
       return new ResponseEntity<> (articleVenduService.findAllArticles(),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{idArticle}")
    public void deleteAccountByAdmin(@PathVariable("idArticle") Integer id) {
        articleVenduService.deleteArticleById(id);
    }

}

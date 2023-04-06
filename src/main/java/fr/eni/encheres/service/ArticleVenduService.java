package fr.eni.encheres.service;

import fr.eni.encheres.dto.create.CreateArticleVenduDto;
import fr.eni.encheres.dto.response.ResponseArticleVenduDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

public interface ArticleVenduService {
        ResponseArticleVenduDto saveArticle(@Valid CreateArticleVenduDto articleVenduDto, Principal principal);
        ResponseArticleVenduDto updateArticle(@Valid CreateArticleVenduDto articleVenduDto, Integer principal);

        List<ResponseArticleVenduDto> findAllArticles();
}

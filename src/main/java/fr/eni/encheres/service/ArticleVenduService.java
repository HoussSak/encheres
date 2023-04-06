package fr.eni.encheres.service;

import fr.eni.encheres.dto.create.CreateArticleVenduDto;
import fr.eni.encheres.dto.response.ResponseArticleVenduDto;
import jakarta.validation.Valid;

import java.security.Principal;

public interface ArticleVenduService {
        ResponseArticleVenduDto saveArticle(@Valid CreateArticleVenduDto articleVenduDto, Principal principal);
}

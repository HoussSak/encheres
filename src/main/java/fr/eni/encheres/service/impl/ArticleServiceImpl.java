package fr.eni.encheres.service.impl;

import fr.eni.encheres.common.UtilisateurConnecte;
import fr.eni.encheres.dto.create.CreateArticleVenduDto;
import fr.eni.encheres.dto.response.ResponseArticleVenduDto;
import fr.eni.encheres.mapper.ArticleVenduMapper;
import fr.eni.encheres.model.ArticleVendu;
import fr.eni.encheres.model.Retrait;
import fr.eni.encheres.model.Utilisateur;
import fr.eni.encheres.repository.ArticleVenduRepository;
import fr.eni.encheres.repository.RetraitRepository;
import fr.eni.encheres.repository.UserRepository;
import fr.eni.encheres.service.ArticleVenduService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleVenduService {
    private final UtilisateurConnecte utilisateurConnecte;
    private final UserRepository userRepository;
    private final ArticleVenduRepository articleVenduRepository;
    private final ArticleVenduMapper articleVenduMapper;
    private final RetraitRepository retraitRepository;

    public ArticleServiceImpl(UtilisateurConnecte utilisateurConnecte, UserRepository userRepository, ArticleVenduRepository articleVenduRepository, ArticleVenduMapper articleVenduMapper, RetraitRepository retraitRepository) {
        this.utilisateurConnecte = utilisateurConnecte;
        this.userRepository = userRepository;
        this.articleVenduRepository = articleVenduRepository;
        this.articleVenduMapper = articleVenduMapper;
        this.retraitRepository = retraitRepository;
    }

    @Override
    @Transactional
    public ResponseArticleVenduDto saveArticle(CreateArticleVenduDto articleVenduDto, Principal principal) {
        ArticleVendu articleVendu = ArticleVenduMapper.articleVenduDtoToArticleVendu(articleVenduDto);
        Integer actualUserId =  utilisateurConnecte.getUserConnectedId(principal);
        Optional<Utilisateur> existingUser = userRepository.findById(actualUserId);
        articleVendu.setUtilisateur(existingUser.get());
        articleVendu.setRetrait(getRetraitByDefault(articleVendu,existingUser.get()));
        ArticleVendu savedArticle = articleVenduRepository.save(articleVendu);
        return ArticleVenduMapper.articleVenduToArticleVenduDto(savedArticle);
    }

    private Retrait getRetraitByDefault(ArticleVendu savedArticle, Utilisateur utilisateur) {
        return Retrait.builder()
                .adresse(utilisateur.getAdresse())
                .articleVendu(savedArticle)
                .build();
    }


}

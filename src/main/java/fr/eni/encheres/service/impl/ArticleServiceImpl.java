package fr.eni.encheres.service.impl;

import fr.eni.encheres.common.UtilisateurConnecte;
import fr.eni.encheres.dto.create.CreateArticleVenduDto;
import fr.eni.encheres.dto.response.ResponseArticleVenduDto;
import fr.eni.encheres.mapper.ArticleVenduMapper;
import fr.eni.encheres.model.ArticleVendu;
import fr.eni.encheres.model.Categorie;
import fr.eni.encheres.model.Retrait;
import fr.eni.encheres.model.Utilisateur;
import fr.eni.encheres.repository.ArticleVenduRepository;
import fr.eni.encheres.repository.CategoryRepository;
import fr.eni.encheres.repository.RetraitRepository;
import fr.eni.encheres.repository.UserRepository;
import fr.eni.encheres.service.ArticleVenduService;
import fr.eni.encheres.service.helper.ArticlevenduServiceHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleVenduService {
    private final UtilisateurConnecte utilisateurConnecte;
    private final UserRepository userRepository;
    private final ArticleVenduRepository articleVenduRepository;
    private final ArticleVenduMapper articleVenduMapper;
    private final RetraitRepository retraitRepository;
    private final ArticlevenduServiceHelper articlevenduServiceHelper;
    private final CategoryRepository categoryRepository;

    public ArticleServiceImpl(UtilisateurConnecte utilisateurConnecte, UserRepository userRepository, ArticleVenduRepository articleVenduRepository, ArticleVenduMapper articleVenduMapper, RetraitRepository retraitRepository, ArticlevenduServiceHelper articlevenduServiceHelper, CategoryRepository categoryRepository) {
        this.utilisateurConnecte = utilisateurConnecte;
        this.userRepository = userRepository;
        this.articleVenduRepository = articleVenduRepository;
        this.articleVenduMapper = articleVenduMapper;
        this.retraitRepository = retraitRepository;
        this.articlevenduServiceHelper = articlevenduServiceHelper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public ResponseArticleVenduDto saveArticle(CreateArticleVenduDto articleVenduDto, Principal principal) {
        Categorie categorie = categoryRepository.findByLibelle(articleVenduDto.getArticleCategorie());
        ArticleVendu articleVendu = ArticleVenduMapper.articleVenduDtoToArticleVendu(articleVenduDto,categorie);
        Integer actualUserId =  utilisateurConnecte.getUserConnectedId(principal);
        Optional<Utilisateur> existingUser = userRepository.findById(actualUserId);
        articleVendu.setUtilisateur(existingUser.get());
        articleVendu.setRetrait(getRetraitByDefault(articleVendu,existingUser.get()));
        articleVendu.setCategorie(articleVendu.getCategorie());
        ArticleVendu savedArticle = articleVenduRepository.save(articleVendu);
        log.info("Article successfully saved with id : {}", savedArticle.getNoArticle());
        //articlevenduServiceHelper.createAndSaveNewEnchere(savedArticle);
        return ArticleVenduMapper.articleVenduToArticleVenduDto(savedArticle);
    }

    @Override
    public ResponseArticleVenduDto updateArticle(CreateArticleVenduDto articleVenduDto, Integer id) {
        if (id==null) {
            log.error("article ID is null");
            return null;
        }
        Categorie categorie = categoryRepository.findByLibelle(articleVenduDto.getArticleCategorie());
        Optional<ArticleVendu> existingArticle = articleVenduRepository.findById(id);
        ArticleVendu articleVendu = ArticleVenduMapper.updateArticle(articleVenduDto,existingArticle.get(),categorie);
        ArticleVendu savedArticle = articleVenduRepository.save(articleVendu);
        return ArticleVenduMapper.articleVenduToArticleVenduDto(savedArticle);
    }

    @Override
    public List<ResponseArticleVenduDto> findAllArticles() {
        return articlevenduServiceHelper.getArticleList();
    }

    @Override
    public void deleteArticleById(Integer id) {
        if (id == null) {
            log.error("Article with ID = "+id+" not found ");}
        articleVenduRepository.deleteById(id);
    }


    private Retrait getRetraitByDefault(ArticleVendu savedArticle, Utilisateur utilisateur) {
        return Retrait.builder()
                .adresse(utilisateur.getAdresse())
                .articleVendu(savedArticle)
                .build();
    }


}

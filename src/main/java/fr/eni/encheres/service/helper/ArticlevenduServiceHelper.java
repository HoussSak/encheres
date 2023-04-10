package fr.eni.encheres.service.helper;

import fr.eni.encheres.dto.response.ResponseArticleVenduDto;
import fr.eni.encheres.dto.response.ResponseEnchereDto;
import fr.eni.encheres.mapper.ArticleVenduMapper;
import fr.eni.encheres.mapper.EnchereMapper;
import fr.eni.encheres.mapper.UtilisateurMapper;
import fr.eni.encheres.model.ArticleVendu;
import fr.eni.encheres.model.Enchere;
import fr.eni.encheres.model.EnchereId;
import fr.eni.encheres.repository.ArticleVenduRepository;
import fr.eni.encheres.repository.EnchereRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ArticlevenduServiceHelper {
    private final EnchereRepository enchereRepository;
    private final ArticleVenduRepository articleVenduRepository;
    private final EnchereMapper enchereMapper;
    private final UtilisateurMapper utilisateurMapper;

    public ArticlevenduServiceHelper(EnchereRepository enchereRepository, ArticleVenduRepository articleVenduRepository, EnchereMapper enchereMapper, UtilisateurMapper utilisateurMapper) {
        this.enchereRepository = enchereRepository;
        this.articleVenduRepository = articleVenduRepository;
        this.enchereMapper = enchereMapper;
        this.utilisateurMapper = utilisateurMapper;
    }
    @Transactional
    public void createAndSaveNewEnchere(ArticleVendu savedArticle) {
        Enchere enchereToSave = Enchere.builder()
                .id(new EnchereId(savedArticle.getUtilisateur().getId(),savedArticle.getNoArticle()))
                .dateEnchere(savedArticle.getDateFinEncheres())
                .montantEnchere(savedArticle.getPrixInitial())
                .utilisateur(savedArticle.getUtilisateur())
                .articleVendu(savedArticle)
                .build();
        Enchere savedEnchere = enchereRepository.save(enchereToSave);
        log.info("Enchere successfully saved for article with id : {}", savedEnchere.getArticleVendu().getNoArticle());

    }

    public List<ResponseArticleVenduDto> getArticleList() {
       return  articleVenduRepository.findAll().stream()
                .map(article->ArticleVenduMapper.articleVenduToArticleVenduDtoWithEnchereField(article,getEnchere(article)))
                .collect(Collectors.toList());
    }
    public ResponseEnchereDto getEnchere (ArticleVendu articleVendu) {
        Enchere enchereDesc = enchereRepository.findFirstByArticleVenduOrderByMontantEnchereDesc(articleVendu);
        return EnchereMapper.enchereToResponseEnchereDto(enchereDesc);
    }
}

package fr.eni.encheres.service.impl;

import fr.eni.encheres.common.UtilisateurConnecte;
import fr.eni.encheres.dto.response.ResponseEnchereDto;
import fr.eni.encheres.exception.EntityNotFoundException;
import fr.eni.encheres.exception.ErrorCodes;
import fr.eni.encheres.mapper.EnchereMapper;
import fr.eni.encheres.model.ArticleVendu;
import fr.eni.encheres.model.Enchere;
import fr.eni.encheres.model.EnchereId;
import fr.eni.encheres.model.Utilisateur;
import fr.eni.encheres.repository.ArticleVenduRepository;
import fr.eni.encheres.repository.EnchereRepository;
import fr.eni.encheres.repository.UserRepository;
import fr.eni.encheres.service.helper.EnchereServiceHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class EnchereServiceImpl implements EnchereService {
    private final UserRepository userRepository;
    private final ArticleVenduRepository articleVenduRepository;
    private final EnchereRepository enchereRepository;
    private final UtilisateurConnecte utilisateurConnecte;
    private final EnchereServiceHelper enchereServiceHelper;

    public EnchereServiceImpl(UserRepository userRepository, ArticleVenduRepository articleVenduRepository, EnchereRepository enchereRepository, UtilisateurConnecte utilisateurConnecte, EnchereServiceHelper enchereServiceHelper) {
        this.userRepository = userRepository;
        this.articleVenduRepository = articleVenduRepository;
        this.enchereRepository = enchereRepository;
        this.utilisateurConnecte = utilisateurConnecte;
        this.enchereServiceHelper = enchereServiceHelper;
    }

    @Override
    public void addNewEnchere(Integer idArticle,Integer montantProposé,Principal principal) {
        Integer actualUserId =  utilisateurConnecte.getUserConnectedId(principal);
        Utilisateur utilisateur = userRepository.findById(actualUserId).orElseThrow(() ->
                new EntityNotFoundException("Utilisateur with ID = "+actualUserId+" not found", ErrorCodes.UTILISATEUR_NOT_FOUND));
        ArticleVendu articleVendu = articleVenduRepository.findById(idArticle).orElseThrow(() ->
                new EntityNotFoundException("Article with ID = "+idArticle+" not found", ErrorCodes.ARTICLE_NOT_FOUND));
        int nouveauCredit = enchereServiceHelper.VerifyCredit(montantProposé, utilisateur, articleVendu);
        Enchere meilleureEnchere = enchereRepository.findFirstByArticleVenduOrderByMontantEnchereDesc(articleVendu);
        refundingVerification(meilleureEnchere);
        utilisateur.setCredit(nouveauCredit);
        userRepository.save(utilisateur);

        saveEnchere(montantProposé, utilisateur, articleVendu);
    }

    @Override
    public List<ResponseEnchereDto> findAllEnchere(Integer idArticle) {
        ArticleVendu articleVendu = articleVenduRepository.findById(idArticle).orElseThrow(() ->
                new EntityNotFoundException("Article with ID = "+idArticle+" not found", ErrorCodes.ARTICLE_NOT_FOUND));
        return enchereRepository.findByArticleVenduOrderByMontantEnchereDesc(articleVendu)
                .stream()
                .map(EnchereMapper::enchereToResponseEnchereDto)
                .collect(Collectors.toList());
    }

    private void refundingVerification(Enchere meilleureEnchere) {
        if (meilleureEnchere != null) {
            Utilisateur meilleurEncherisseur = meilleureEnchere.getUtilisateur();
            meilleurEncherisseur.setCredit(meilleurEncherisseur.getCredit() + meilleureEnchere.getMontantEnchere());
            userRepository.save(meilleurEncherisseur);
        }
    }
    private void saveEnchere(Integer montantProposé, Utilisateur utilisateur, ArticleVendu articleVendu) {
        Enchere enchereToSave = Enchere.builder()
                .id(new EnchereId(utilisateur.getId(), articleVendu.getNoArticle()))
                .utilisateur(utilisateur)
                .articleVendu(articleVendu)
                .montantEnchere(montantProposé)
                .dateEnchere(LocalDate.now())
                .build();
        enchereRepository.save(enchereToSave);
    }


}

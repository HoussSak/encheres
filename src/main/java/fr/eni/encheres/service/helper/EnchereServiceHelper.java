package fr.eni.encheres.service.helper;

import fr.eni.encheres.exception.InvalidEntityException;
import fr.eni.encheres.model.ArticleVendu;
import fr.eni.encheres.model.Enchere;
import fr.eni.encheres.model.Utilisateur;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EnchereServiceHelper {
    public int VerifyCredit(Integer montantProposé, Utilisateur utilisateur, ArticleVendu articleVendu) {
        if (montantProposé <= articleVendu.getNoArticle()) {
            log.error("le montant proposé: {} est infériere ai prix de vente: {}", montantProposé, articleVendu.getPrixVente());
            throw new InvalidEntityException("le montant proposé est infériere ai prix de vente");
        }
        int nouveauCredit = utilisateur.getCredit() - montantProposé;
        if (nouveauCredit < 0) {
            log.error("le montant proposé: {} est superieur au crédit de l utilisateur: {}", montantProposé, utilisateur.getCredit());
            throw new InvalidEntityException("le montant proposé est supperieur au crédit de l utilisateur");
        }
        return nouveauCredit;
    }
}

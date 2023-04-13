package fr.eni.encheres.service.helper;

import fr.eni.encheres.common.UtilisateurConnecte;
import fr.eni.encheres.dto.create.CreateUtilisateurDto;
import fr.eni.encheres.dto.response.ResponseEnchereDto;
import fr.eni.encheres.dto.response.ResponseUtilisateurDto;
import fr.eni.encheres.exception.EntityNotFoundException;
import fr.eni.encheres.exception.ErrorCodes;
import fr.eni.encheres.jwt.JwtController;
import fr.eni.encheres.jwt.JwtFilter;
import fr.eni.encheres.jwt.JwtUtils;
import fr.eni.encheres.mapper.UtilisateurMapper;
import fr.eni.encheres.model.UserHistory;
import fr.eni.encheres.model.Utilisateur;
import fr.eni.encheres.repository.UserHistoryRepository;
import fr.eni.encheres.repository.UserRepository;
import fr.eni.encheres.service.impl.EnchereService;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UtilisateurServiceHelper {
    private final UserRepository userRepository;
    private final UtilisateurMapper utilisateurMapper;
    private final ArticlevenduServiceHelper articlevenduServiceHelper;
    private final JwtController jwtController;
    private final UtilisateurConnecte utilisateurConnecte;
    private final EnchereService enchereService;
    private final UserHistoryRepository userHistoryRepository;

    private final JwtUtils jwtUtils;

    public UtilisateurServiceHelper(UserRepository userRepository, UtilisateurMapper utilisateurMapper, ArticlevenduServiceHelper articlevenduServiceHelper, JwtController jwtController, UtilisateurConnecte utilisateurConnecte, EnchereService enchereService, UserHistoryRepository userHistoryRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.utilisateurMapper = utilisateurMapper;
        this.articlevenduServiceHelper = articlevenduServiceHelper;
        this.jwtController = jwtController;
        this.utilisateurConnecte = utilisateurConnecte;
        this.enchereService = enchereService;
        this.userHistoryRepository = userHistoryRepository;
        this.jwtUtils = jwtUtils;
    }
    @Transactional
    public Tuple2<ResponseUtilisateurDto, HttpHeaders> saveUtlisateur(CreateUtilisateurDto utilisateurDto) {
        Utilisateur savedUser = userRepository.save(UtilisateurMapper.createUtilisateurDtoToUtilisateur(utilisateurDto));
        log.info("User is created with id: {}", savedUser.getId());
        ResponseUtilisateurDto user = UtilisateurMapper.utilisateurToUtilisateurDtoResponse(savedUser);
        Authentication authentication = jwtController.logUser(utilisateurDto.getEmail(), utilisateurDto.getMotDePasse());
        String jwt = jwtUtils.generateToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return Tuple.of(user,httpHeaders);
    }
    public ResponseUtilisateurDto findById(Integer id) {
        Optional<Utilisateur> utilisateur = userRepository.findById(id);
        ResponseUtilisateurDto utilisateurDto = UtilisateurMapper.utilisateurToUtilisateurDtoResponse(utilisateur.get());
        return Optional.of(utilisateurDto).orElseThrow(()->
                new EntityNotFoundException("Utilisateur with ID = "+id+" not found", ErrorCodes.UTILISATEUR_NOT_FOUND));
    }

    public void delete(Principal principal) {
        Integer actualUserId =  utilisateurConnecte.getUserConnectedId(principal);
        if (actualUserId == null) {
            log.error("Utilisateur with ID = "+actualUserId+" not found ");
        }
        Utilisateur user = userRepository.findById(actualUserId).get();

        Integer refundSum = articlevenduServiceHelper.getArticleList().stream().map(responseArticleVenduDto -> {
            Integer id = responseArticleVenduDto.getEnchere().getId();
            Integer montantEnchere = responseArticleVenduDto.getEnchere().getMontantEnchere();
            Integer montantArembourser=0;
            if (id != null && id == actualUserId) {
                log.info("bid found for user with ID:{} with an amount of: {} for article id: {}", id, montantEnchere, responseArticleVenduDto.getId());
                montantArembourser = montantEnchere;
            }
            return montantArembourser;
        }).mapToInt(Integer::intValue).sum();

        if (refundSum == 0) {
            log.info("no bid is found for user with ID:{}", actualUserId);
            log.info("Processing deleting acount for user with id:{}", actualUserId);
            UserHistory userHistory = UtilisateurMapper.utilisateurToUserHistory(user);
            UserHistory savecUserHistory = userHistoryRepository.save(userHistory);
            log.info("user history saved with id: {}",savecUserHistory.getId());
            userRepository.deleteById(actualUserId);
        } else {
            log.info("Processing refunding an amount of: {} to  user user with id:{}", refundSum,actualUserId);
            user.setCredit(refundSum);
            userRepository.save(user);
            log.info("user successfully refunded");
            UserHistory userHistory = UtilisateurMapper.utilisateurToUserHistory(user);
            UserHistory savecUserHistory = userHistoryRepository.save(userHistory);
            log.info("user history saved with id: {}",savecUserHistory.getId());
            userRepository.deleteById(actualUserId);
        }


         articlevenduServiceHelper.getArticleList().forEach(responseArticleVenduDto -> {
            List<ResponseEnchereDto> allEnchere = enchereService.findAllEnchere(responseArticleVenduDto.getId());
            allEnchere.forEach(responseEnchereDto -> {
                Utilisateur enchereUser = userRepository.findById(responseEnchereDto.getId()).get();
                if (enchereUser.getCredit() >= responseEnchereDto.getMontantEnchere()) {
                    Integer newCredit = enchereUser.getCredit() - responseEnchereDto.getMontantEnchere();
                    enchereUser.setCredit(newCredit);
                    userRepository.save(enchereUser);
                }
            });
         });
    }

    public void deleteAccount(Integer id) {
        if (id == null) {
            log.error("Utilisateur with ID = "+id+" not found ");}
        userRepository.deleteById(id);
    }

    public List<ResponseUtilisateurDto> readAllUsers() {
        return userRepository.findAll().stream()
                .map(UtilisateurMapper::utilisateurToUtilisateurDtoResponse).
                collect(Collectors.toList());
    }
    @Transactional
    public ResponseUtilisateurDto updateUserByAdmin(CreateUtilisateurDto utilisateurDto,Integer id ) {
        Optional<Utilisateur> optionalUser = userRepository.findById(id);
        Utilisateur foundUser = optionalUser.orElseThrow(() -> new EntityNotFoundException("Utilisateur with ID = " + id + " not found", ErrorCodes.UTILISATEUR_NOT_FOUND));
        Utilisateur updatedUser = UtilisateurMapper.updateProfile(utilisateurDto,foundUser);
        Utilisateur savedUser = userRepository.save(updatedUser);
        log.info("User with ID = {} is updated", savedUser.getId());
        return UtilisateurMapper.utilisateurToUtilisateurDtoResponse(savedUser);
    }
    @Transactional
    public Tuple2<ResponseUtilisateurDto, HttpHeaders> updateUser(CreateUtilisateurDto utilisateurDto, Principal principal) {
        Integer id =  utilisateurConnecte.getUserConnectedId(principal);
        Optional<Utilisateur> optionalUser = userRepository.findById(id);
        Utilisateur foundUser = optionalUser.orElseThrow(() -> new EntityNotFoundException("Utilisateur with ID = " + id + " not found", ErrorCodes.UTILISATEUR_NOT_FOUND));
        Utilisateur updatedUser = UtilisateurMapper.updateProfile(utilisateurDto,foundUser);
        Utilisateur savedUser = userRepository.save(updatedUser);
        log.info("User with ID = {} is updated", savedUser.getId());
        HttpHeaders httpHeaders = getHttpHeaders(utilisateurDto,savedUser);
        ResponseUtilisateurDto responseUtilisateurDto = UtilisateurMapper.utilisateurToUtilisateurDtoResponse(savedUser);
        return Tuple.of(responseUtilisateurDto,httpHeaders);
    }

    private HttpHeaders getHttpHeaders(CreateUtilisateurDto utilisateurDto, Utilisateur savedUser) {
        if (utilisateurDto.getEmail() == null) {
            return new HttpHeaders();
        }
        Authentication authentication = jwtController.logUser(savedUser.getEmail(), utilisateurDto.getMotDePasse());
        String jwt = jwtUtils.generateToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return httpHeaders;
    }
}

package challenge.platform.backend.service.impl;

import challenge.platform.backend.entity.Account;
import challenge.platform.backend.entity.AccountChallenge;
import challenge.platform.backend.entity.AccountCompletedChallenges;
import challenge.platform.backend.entity.Badge;
import challenge.platform.backend.exception.ResourceNotFoundException;
import challenge.platform.backend.payload.AccountChallengeDto;
import challenge.platform.backend.repository.AccountChallengeRepository;
import challenge.platform.backend.repository.AccountCompletedChallengesRepository;
import challenge.platform.backend.repository.AccountRepository;
import challenge.platform.backend.repository.BadgesRepository;
import challenge.platform.backend.service.AccountChallengeService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Transactional
@Service
public class AccountChallengeServiceImpl implements AccountChallengeService {
    private ModelMapper modelMapper;
    private AccountChallengeRepository accountChallengeRepository;
    private AccountCompletedChallengesRepository accountCompletedChallengesRepository;
    private BadgesRepository badgesRepository;
    private AccountRepository accountRepository;


    public AccountChallengeServiceImpl(
            AccountChallengeRepository accountChallengeRepository,
            ModelMapper modelMapper,
            AccountCompletedChallengesRepository accountCompletedChallengesRepository, BadgesRepository badgesRepository, AccountRepository accountRepository) {
        this.accountChallengeRepository = accountChallengeRepository;
        this.modelMapper = modelMapper;
        this.accountCompletedChallengesRepository = accountCompletedChallengesRepository;
        this.badgesRepository = badgesRepository;
        this.accountRepository = accountRepository;
    }

    private AccountChallengeDto mapToDto(AccountChallenge accountChallenge) {
        return modelMapper.map(accountChallenge, AccountChallengeDto.class);
    }


    private AccountChallenge mapToEntity(AccountChallengeDto accountChallengeDto) {
        return modelMapper.map(accountChallengeDto, AccountChallenge.class);
    }


    @Override
    public AccountChallengeDto createAccountChallenge(AccountChallengeDto accountChallengeDto) {

        AccountChallenge accountChallengeToSave = createAccountChallengeToSave(accountChallengeDto);
        AccountChallenge createdAccountChallenge = accountChallengeRepository.save(accountChallengeToSave);

        log.info("Challenge with id {} was started from account id {}",
                createdAccountChallenge.getChallengeId(),
                accountChallengeDto.getAccountId()
        );
        return mapToDto(createdAccountChallenge);
    }

    private AccountChallenge createAccountChallengeToSave(AccountChallengeDto accountChallengeDto) {
        AccountChallenge mappedAccountChallenge = mapToEntity(accountChallengeDto);
        mappedAccountChallenge.setStarted(LocalDate.now());
        return mappedAccountChallenge;
    }


    @Override
    public List<AccountChallengeDto> getAccountChallengesByAccountId(long accountId) {
        List<AccountChallenge> entities = accountChallengeRepository.findAllChallengesByAccountId(accountId);
        List<AccountChallengeDto> accountChallengeDtoList = entities.stream().map(entity -> mapToDto(entity)).toList();
        return accountChallengeDtoList;
    }

    @Override
    public void deleteAccountChallengeById(long accountId, long challengeId) {
        AccountChallenge found = accountChallengeRepository.findByAccountIdAndChallengeId(accountId, challengeId)
                .orElseThrow(() -> new ResourceNotFoundException("AccountChallenge", "accountId & challengeId", accountId));
        accountChallengeRepository.delete(found);
        log.info("Challenge with id {} was aborted", found.getChallengeId());
    }

    @Override
    public AccountChallengeDto updateAccountChallenge(AccountChallengeDto accountChallengeDto) {
        Long accountId = accountChallengeDto.getAccountId();
        Long challengeId = accountChallengeDto.getChallengeId();
        AccountChallenge found = accountChallengeRepository.findByAccountIdAndChallengeId(accountId, challengeId)
                .orElseThrow(() -> new ResourceNotFoundException("AccountChallenge", "accountId", accountId));
        found.setCompleted(LocalDate.now());

        AccountChallenge updatedChallenge = accountChallengeRepository.save(found);

        updateTableForAccountCompletedChallenges(accountChallengeDto, accountId);

        log.info("Challenge with id {} completed from {}", updatedChallenge.getChallengeId(), accountId);
        return mapToDto(updatedChallenge);
    }

    private void updateTableForAccountCompletedChallenges(AccountChallengeDto accountChallengeDto, Long accountId) {
        Optional<AccountCompletedChallenges> accountCompletedChallenges = accountCompletedChallengesRepository.findByAccountId(accountId);
        if (accountCompletedChallenges.isPresent()) {
            List<Long> updatedIds = accountCompletedChallenges.get().getCompletedChallenges();
            updatedIds.add(accountChallengeDto.getChallengeId());
            AccountCompletedChallenges objectToSave = accountCompletedChallenges.get();
            objectToSave.setCompletedChallenges(updatedIds);
            accountCompletedChallengesRepository.save(objectToSave);

            log.info("Update completed for account id {} challenge id {}", accountId, accountChallengeDto.getChallengeId());
            checkForBadgeAddingToAccount(accountId, updatedIds.size());

        } else {
            AccountCompletedChallenges newObjectToSave = new AccountCompletedChallenges();
            newObjectToSave.setAccountId(accountId);
            List<Long> completedChallengesIds = new ArrayList<>();
            completedChallengesIds.add(accountChallengeDto.getChallengeId());
            newObjectToSave.setCompletedChallenges(completedChallengesIds);
            accountCompletedChallengesRepository.save(newObjectToSave);

            log.info("Adds first completed challenge (id {}) to the table for account with id {}",
                    accountChallengeDto.getChallengeId(),
                    accountId);
        }
    }

    private void checkForBadgeAddingToAccount(Long accountId, int numberOfUpdatedIds) {
        Account accountForBadgeUpdate = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "accountId", accountId));
        List<Long> badgeList = accountForBadgeUpdate.getBadges();

//        List<Badge> allBadges = badgesRepository.findAll();


        // badges
        Badge beginner = badgesRepository.findByName("BEGINNER");
        Badge intermediate = badgesRepository.findByName("INTERMEDIATE");
        Badge profi = badgesRepository.findByName("PROFI");

        log.info("Account with id {} has completed {} challenges.", accountId, numberOfUpdatedIds);
        if (numberOfUpdatedIds < 3) {
            log.info("Account with id {} is a NEWBI and still has no badges.", accountId);
        } else if (numberOfUpdatedIds >= beginner.getCondition() &&
                numberOfUpdatedIds < intermediate.getCondition()) {
            assignBadge(accountForBadgeUpdate, badgeList, beginner);
            log.info("Account with id {} has {} badge.", accountId, beginner.getName());
        } else if (numberOfUpdatedIds >= intermediate.getCondition()
                && numberOfUpdatedIds < profi.getCondition()) {
            assignBadge(accountForBadgeUpdate, badgeList, intermediate);
            log.info("Account with id {} has {} badge.", accountId, intermediate.getName());
        } else {
            assignBadge(accountForBadgeUpdate, badgeList, profi);
            log.info("Account with id {} has {} badge.", accountId, profi.getName());
        }
    }

    private boolean checkIfBadgeIsAlreadyAssigned(List<Long> badgeList, Long badgeId) {
        return badgeList.contains(badgeId);
    }

    private void assignBadge(Account toUpdate, List<Long> badgeList, Badge badgeType) {
        if (!checkIfBadgeIsAlreadyAssigned(badgeList, badgeType.getId())) {
            badgeList.add(badgeType.getId());
            toUpdate.setBadges(badgeList);
            accountRepository.save(toUpdate);
            log.info("user {} updated, earned {}", toUpdate.getUsername(), badgeType.getName());
        }
    }

//    private void assignBadge2(Account toUpdate, List<Badge> allBadges) {
//        List<Long> badgeIds = allBadges.stream().map(badge -> badge.getId()).toList();
//        List<Long> alreadyOwnedBadgeIds = toUpdate.getBadges();
//        if (!checkIfBadgeIsAlreadyAssigned(badgeList, badgeType.getId())) {
//            badgeList.add(badgeType.getId());
//            toUpdate.setBadges(badgeList);
//            accountRepository.save(toUpdate);
//            log.info("user {} updated, earned {}", toUpdate.getUsername(), badgeType.getName());
//        }
//    }


}

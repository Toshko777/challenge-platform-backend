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

        List<Badge> allBadges = badgesRepository.findAll();

        log.info("Account with id {} has completed {} challenges.", accountId, numberOfUpdatedIds);

        assignBadge(accountForBadgeUpdate, allBadges, numberOfUpdatedIds);
    }

    private void assignBadge(Account accountToUpdate, List<Badge> allBadges, int numberOfCompletedChallenges) {
        List<Long> ownedBadgeIds = new ArrayList<>(accountToUpdate.getBadges());

        for (Badge badge : allBadges) {
            // if the badge is not already earned AND condition is met -> add it to the list
            if (!ownedBadgeIds.contains(badge.getId()) && (numberOfCompletedChallenges >= badge.getCondition())) {
                ownedBadgeIds.add(badge.getId());
                log.info("user {} earned {}", accountToUpdate.getUsername(), badge.getName());
            }
        }
        if (accountToUpdate.getBadges().size() < ownedBadgeIds.size()) {
            accountToUpdate.setBadges(ownedBadgeIds);
            Account update = accountRepository.save(accountToUpdate);
            log.info("user {} updated, new number of badges {}", accountToUpdate.getUsername(), update.getBadges().size());

        }

    }

    private AccountChallengeDto mapToDto(AccountChallenge accountChallenge) {
        return modelMapper.map(accountChallenge, AccountChallengeDto.class);
    }


    private AccountChallenge mapToEntity(AccountChallengeDto accountChallengeDto) {
        return modelMapper.map(accountChallengeDto, AccountChallenge.class);
    }


}

package challenge.platform.backend.service.impl;

import challenge.platform.backend.entity.AccountChallenge;
import challenge.platform.backend.entity.AccountCompletedChallenges;
import challenge.platform.backend.entity.Role;
import challenge.platform.backend.exception.ResourceNotFoundException;
import challenge.platform.backend.payload.AccountChallengeDto;
import challenge.platform.backend.payload.RoleDto;
import challenge.platform.backend.repository.AccountChallengeRepository;
import challenge.platform.backend.repository.AccountCompletedChallengesRepository;
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


    public AccountChallengeServiceImpl(
            AccountChallengeRepository accountChallengeRepository,
            ModelMapper modelMapper,
            AccountCompletedChallengesRepository accountCompletedChallengesRepository) {
        this.accountChallengeRepository = accountChallengeRepository;
        this.modelMapper = modelMapper;
        this.accountCompletedChallengesRepository = accountCompletedChallengesRepository;
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

        log.info("Challenge with id {} was started", createdAccountChallenge.getChallengeId());
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
    public void deleteAccountChallengeById(long id) {
        AccountChallenge found = accountChallengeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("AccountChallenge", "id", id));
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

        log.info("Challenge with id {} completed", updatedChallenge.getChallengeId());
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

            log.info("Updates completed challenges list for account with id {}", accountId);
            checkForBadgeAddingToAccount(accountId, updatedIds);

        } else {
            AccountCompletedChallenges newObjectToSave = new AccountCompletedChallenges();
            newObjectToSave.setAccountId(accountId);
            List<Long> completedChallengesIds = new ArrayList<>();
            completedChallengesIds.add(accountChallengeDto.getChallengeId());
            newObjectToSave.setCompletedChallenges(completedChallengesIds);
            accountCompletedChallengesRepository.save(newObjectToSave);

            log.info("Adds first completed challenges object to the table for account with id {}", accountId);
        }
    }

    private static void checkForBadgeAddingToAccount(Long accountId, List<Long> updatedIds) {
        // todo add badge checking and badge assigning
        log.info("Account with id {} has completed {} challenges.", accountId, updatedIds.size());
        if (updatedIds.size() < 3) {
            log.info("Account with id {} has beginner badge.", accountId);
        } else if (updatedIds.size() < 7) {
            log.info("Account with id {} has intermediate badge.", accountId);
        } else {
            log.info("Account with id {} has profi badge.", accountId);
        }
    }

}

package challenge.platform.backend.service.impl;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;


import challenge.platform.backend.entity.AccountChallenge;
import challenge.platform.backend.exception.ResourceNotFoundException;
import challenge.platform.backend.payload.AccountChallengeDto;

import challenge.platform.backend.repository.AccountChallengeRepository;
import challenge.platform.backend.service.AccountChallengeService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@Service
public class AccountChallengeServiceImpl implements AccountChallengeService {
    private ModelMapper modelMapper;
    private AccountChallengeRepository accountChallengeRepository;
   
   

public AccountChallengeServiceImpl(
            AccountChallengeRepository accountChallengeRepository,
            ModelMapper modelMapper
           
){
    this.accountChallengeRepository = accountChallengeRepository;
    this.modelMapper = modelMapper;
    
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

        log.info("Challenge with id {} was started");
        return mapToDto(createdAccountChallenge);
    }
    private AccountChallenge createAccountChallengeToSave(AccountChallengeDto accountChallengeDto) {
        AccountChallenge mappedAccountChallenge = mapToEntity(accountChallengeDto); 
        mappedAccountChallenge.setStarted(LocalDate.now());     
        return mappedAccountChallenge;
    }
   

 

    @Override
    public AccountChallengeDto getAccountChallengeById(long id) {
        
        AccountChallenge accountChallenge = accountChallengeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("AccountChallenge", "id", id));
        return mapToDto(accountChallenge);
    }

    @Override
    public AccountChallengeDto updateAccountChallenge(long id, AccountChallengeDto accountChallengeDto) {
        AccountChallenge found = accountChallengeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("AccountChallenge", "id", id));
        found.setCompleted(LocalDate.now());
        

        AccountChallenge savedAccountChallenge = accountChallengeRepository.save(found);
        log.info("Challenge with id {} completed");
        return mapToDto(savedAccountChallenge);
    }

    @Override
    public void deleteAccountChallengeById(long id) {
        AccountChallenge found = accountChallengeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("AccountChallenge", "id", id));
        accountChallengeRepository.delete(found);
        log.info("Challenge with id {} was aborted");
    }
    
    
}

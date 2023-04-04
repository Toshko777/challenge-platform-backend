package challenge.platform.backend.service;

import challenge.platform.backend.payload.AccountChallengeDto;


public interface AccountChallengeService {
    
    AccountChallengeDto createAccountChallenge(AccountChallengeDto bookDto);

   

    AccountChallengeDto getAccountChallengeById(long id);

    AccountChallengeDto updateAccountChallenge(long id, AccountChallengeDto accountChallengeDto);

    void deleteAccountChallengeById(long id);
}
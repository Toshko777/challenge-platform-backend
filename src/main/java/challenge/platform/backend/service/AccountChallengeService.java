package challenge.platform.backend.service;

import challenge.platform.backend.payload.AccountChallengeDto;

import java.util.List;


public interface AccountChallengeService {

    AccountChallengeDto createAccountChallenge(AccountChallengeDto bookDto);

    List<AccountChallengeDto> getAccountChallengesByAccountId(long accountId);

    AccountChallengeDto updateAccountChallenge(AccountChallengeDto accountChallengeDto);

    void deleteAccountChallengeById(long accountId, long challengeId);
}
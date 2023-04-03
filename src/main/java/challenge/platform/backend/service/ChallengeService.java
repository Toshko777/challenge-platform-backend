package challenge.platform.backend.service;

import challenge.platform.backend.payload.ChallengeDto;
import challenge.platform.backend.payload.ChallengeResponse;

public interface ChallengeService {
    
    ChallengeDto createChallenge(ChallengeDto bookDto);

    ChallengeResponse getAllChallenges(int pageNo, int pageSize, String sortBy, String sortDir);

    ChallengeDto getChallengeById(long id);

    ChallengeDto updateChallenge(long id, ChallengeDto challengeDto);

    void deleteChallengeById(long id);
}

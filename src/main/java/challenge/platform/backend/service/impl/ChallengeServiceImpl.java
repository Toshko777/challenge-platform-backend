package challenge.platform.backend.service.impl;

import challenge.platform.backend.entity.Challenge;
import challenge.platform.backend.exception.ResourceNotFoundException;
import challenge.platform.backend.payload.ChallengeDto;
import challenge.platform.backend.payload.ChallengeResponse;
import challenge.platform.backend.repository.ChallengesRepository;
import challenge.platform.backend.service.ChallengeService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Slf4j
@Transactional
@Service
public class ChallengeServiceImpl implements ChallengeService {
    private ModelMapper modelMapper;
    private ChallengesRepository challengesRepository;


    public ChallengeServiceImpl(
            ChallengesRepository challengesRepository,
            ModelMapper modelMapper) {
        this.challengesRepository = challengesRepository;
        this.modelMapper = modelMapper;
    }

    private ChallengeDto mapToDto(Challenge challenge) {
        return modelMapper.map(challenge, ChallengeDto.class);
    }


    private Challenge mapToEntity(ChallengeDto challengeDto) {
        return modelMapper.map(challengeDto, Challenge.class);
    }


    @Override
    public ChallengeDto createChallenge(ChallengeDto challengeDto) {
        Challenge challengeToSave = createChallengeToSave(challengeDto);
        Challenge createdChallenge = challengesRepository.save(challengeToSave);

        log.info("Challenge {} with id: {} created and was created by user with id: {}",
                createdChallenge.getName(),
                createdChallenge.getId(),
                createdChallenge.getCreatorId()
        );
        return mapToDto(createdChallenge);
    }

    private Challenge createChallengeToSave(ChallengeDto challengeDto) {
        Challenge mappedChallenge = mapToEntity(challengeDto);
        mappedChallenge.setCreated(LocalDate.now());

        return mappedChallenge;
    }

    @Override
    public ChallengeResponse getAllChallenges(int pageNo, int pageSize, String sortBy, String sortDir) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<Challenge> challenges = challengesRepository.findAll(pageable);

        // get content for page object
        ChallengeResponse challengeResponse = new ChallengeResponse();

        challengeResponse.setContent(challenges.getContent().stream().map(challenge -> mapToDto(challenge)).toList());
        challengeResponse.setPageNo(challenges.getNumber());
        challengeResponse.setPageSize(challenges.getSize());
        challengeResponse.setTotalElements(challenges.getTotalElements());
        challengeResponse.setTotalPages(challenges.getTotalPages());
        challengeResponse.setLast(challenges.isLast());

        return challengeResponse;
    }

    @Override
    public ChallengeDto getChallengeById(long id) {

        Challenge challenge = challengesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Challenge", "id", id));
        return mapToDto(challenge);
    }

    @Override
    public ChallengeDto updateChallenge(long id, ChallengeDto challengeDto) {
        Challenge found = challengesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Challenge", "id", id));
        found.setName(challengeDto.getName());
        found.setDescription(challengeDto.getDescription());

        Challenge saved = challengesRepository.save(found);
        log.info("Challenge with id: {} updated", id);
        return mapToDto(saved);
    }

    @Override
    public void deleteChallengeById(long id) {
        Challenge found = challengesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Challenge", "id", id));
        challengesRepository.delete(found);
        log.info("Challenge with id {} was deleted", id);
    }

}

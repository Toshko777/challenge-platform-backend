package challenge.platform.backend.service.impl;

import challenge.platform.backend.entity.Challenge;
import challenge.platform.backend.exception.ResourceNotFoundException;
import challenge.platform.backend.payload.Ch_createdDto;
import challenge.platform.backend.payload.Ch_createdResponse;
import challenge.platform.backend.repository.ChallengesRepository;
import challenge.platform.backend.service.Ch_createdService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class Ch_createdServiceImpl implements Ch_createdService {

    private ModelMapper modelMapper;
    private ChallengesRepository challengesRepository;

    public Ch_createdServiceImpl(ChallengesRepository challengesRepository, ModelMapper modelMapper) {
        this.challengesRepository = challengesRepository;
        this.modelMapper = modelMapper;
    }

    private Ch_createdDto mapToDto(Challenge challenge) {
        return modelMapper.map(challenge, Ch_createdDto.class);
    }

    
    private Challenge mapToEntity(Ch_createdDto ch_createdDto) {
        return modelMapper.map(ch_createdDto, Challenge.class);
    }

    @Override
    public Ch_createdDto createCh_created(Ch_createdDto ch_createdDto) {
        
        Challenge challengeToCreate = mapToEntity(ch_createdDto);
        
        Challenge createdChallenge = challengesRepository.save(challengeToCreate);

        return mapToDto(createdChallenge);
    }

    @Override
    public Ch_createdResponse getAllCh_createds(int pageNo, int pageSize, String sortBy, String sortDir) {
        
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<Challenge> ch_createds = challengesRepository.findAll(pageable);

        
        Ch_createdResponse ch_createdResponse = new Ch_createdResponse();

        ch_createdResponse.setContent(ch_createds.getContent().stream().map(challenge -> mapToDto(challenge)).toList());
        ch_createdResponse.setPageNo(ch_createds.getNumber());
        ch_createdResponse.setPageSize(ch_createds.getSize());
        ch_createdResponse.setTotalElements(ch_createds.getTotalElements());
        ch_createdResponse.setTotalPages(ch_createds.getTotalPages());
        ch_createdResponse.setLast(ch_createds.isLast());

        return ch_createdResponse;
    }

    @Override
    public Ch_createdDto getCh_createdById(long id) {
        Challenge challenge = challengesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ch_created", "id", id));
        return mapToDto(challenge);
    }

    @Override
    public Ch_createdDto updateCh_created(long id, Ch_createdDto ch_createdDto) {
        Challenge found = challengesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ch_created", "id", id));

        found.setUser_name(ch_createdDto.getUser_name());
        found.setChallenge_name(ch_createdDto.getChallenge_name());
        
        
        Challenge savedBook = challengesRepository.save(found);

        return mapToDto(savedBook);
    }

    @Override
    public void deleteCh_createdById(long id) {
        Challenge found = challengesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ch_comp", "id", id));
        challengesRepository.delete(found);
    }


}
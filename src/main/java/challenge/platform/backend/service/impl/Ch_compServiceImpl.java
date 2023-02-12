package challenge.platform.backend.service.impl;

import challenge.platform.backend.entity.CompletedChallenge;
import challenge.platform.backend.exception.ResourceNotFoundException;
import challenge.platform.backend.payload.Ch_compDto;
import challenge.platform.backend.payload.Ch_compResponse;
import challenge.platform.backend.repository.BadgesRepository;
import challenge.platform.backend.service.Ch_compService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class Ch_compServiceImpl implements Ch_compService {

    private ModelMapper modelMapper;
    private BadgesRepository rolesRepository;

    public Ch_compServiceImpl(BadgesRepository rolesRepository, ModelMapper modelMapper) {
        this.rolesRepository = rolesRepository;
        this.modelMapper = modelMapper;
    }

    private Ch_compDto mapToDto(CompletedChallenge completedChallenge) {
        return modelMapper.map(completedChallenge, Ch_compDto.class);
    }

    
    private CompletedChallenge mapToEntity(Ch_compDto ch_compDto) {
        return modelMapper.map(ch_compDto, CompletedChallenge.class);
    }

    @Override
    public Ch_compDto createCh_comp(Ch_compDto ch_compDto) {
        
        CompletedChallenge completedChallengeToCreate = mapToEntity(ch_compDto);
        
        CompletedChallenge createdCompletedChallenge = rolesRepository.save(completedChallengeToCreate);

        return mapToDto(createdCompletedChallenge);
    }

    @Override
    public Ch_compResponse getAllCh_comps(int pageNo, int pageSize, String sortBy, String sortDir) {
        
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<CompletedChallenge> ch_comps = rolesRepository.findAll(pageable);

        
        Ch_compResponse ch_compResponse = new Ch_compResponse();

        ch_compResponse.setContent(ch_comps.getContent().stream().map(completedChallenge -> mapToDto(completedChallenge)).toList());
        ch_compResponse.setPageNo(ch_comps.getNumber());
        ch_compResponse.setPageSize(ch_comps.getSize());
        ch_compResponse.setTotalElements(ch_comps.getTotalElements());
        ch_compResponse.setTotalPages(ch_comps.getTotalPages());
        ch_compResponse.setLast(ch_comps.isLast());

        return ch_compResponse;
    }

    @Override
    public Ch_compDto getCh_compById(long id) {
        CompletedChallenge completedChallenge = rolesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ch_comp", "id", id));
        return mapToDto(completedChallenge);
    }

    @Override
    public Ch_compDto updateCh_comp(long id, Ch_compDto ch_compDto) {
        CompletedChallenge found = rolesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ch_comp", "id", id));

        found.setUser_name(ch_compDto.getUser_name());
        found.setChallenge_name(ch_compDto.getChallenge_name());
        
        
        CompletedChallenge savedBook = rolesRepository.save(found);

        return mapToDto(savedBook);
    }

    @Override
    public void deleteCh_compById(long id) {
        CompletedChallenge found = rolesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ch_comp", "id", id));
        rolesRepository.delete(found);
    }


}
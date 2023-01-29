package challenge.platform.backend.service.impl;

import challenge.platform.backend.entity.Ch_created;
import challenge.platform.backend.exception.ResourceNotFoundException;
import challenge.platform.backend.payload.Ch_createdDto;
import challenge.platform.backend.payload.Ch_createdResponse;
import challenge.platform.backend.repository.Ch_createdRepository;
import challenge.platform.backend.service.Ch_createdService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class Ch_createdServiceImpl implements Ch_createdService {

    private ModelMapper modelMapper;
    private Ch_createdRepository ch_createdRepository;

    public Ch_createdServiceImpl(Ch_createdRepository ch_createdRepository, ModelMapper modelMapper) {
        this.ch_createdRepository = ch_createdRepository;
        this.modelMapper = modelMapper;
    }

    private Ch_createdDto mapToDto(Ch_created ch_created) {
        return modelMapper.map(ch_created, Ch_createdDto.class);
    }

    
    private Ch_created mapToEntity(Ch_createdDto ch_createdDto) {
        return modelMapper.map(ch_createdDto, Ch_created.class);
    }

    @Override
    public Ch_createdDto createCh_created(Ch_createdDto ch_createdDto) {
        
        Ch_created ch_createdToCreate = mapToEntity(ch_createdDto);
        
        Ch_created createdCh_created = ch_createdRepository.save(ch_createdToCreate);

        return mapToDto(createdCh_created);
    }

    @Override
    public Ch_createdResponse getAllCh_createds(int pageNo, int pageSize, String sortBy, String sortDir) {
        
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<Ch_created> ch_createds = ch_createdRepository.findAll(pageable);

        
        Ch_createdResponse ch_createdResponse = new Ch_createdResponse();

        ch_createdResponse.setContent(ch_createds.getContent().stream().map(ch_created -> mapToDto(ch_created)).toList());
        ch_createdResponse.setPageNo(ch_createds.getNumber());
        ch_createdResponse.setPageSize(ch_createds.getSize());
        ch_createdResponse.setTotalElements(ch_createds.getTotalElements());
        ch_createdResponse.setTotalPages(ch_createds.getTotalPages());
        ch_createdResponse.setLast(ch_createds.isLast());

        return ch_createdResponse;
    }

    @Override
    public Ch_createdDto getCh_createdById(long id) {
        Ch_created ch_created = ch_createdRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ch_created", "id", id));
        return mapToDto(ch_created);
    }

    @Override
    public Ch_createdDto updateCh_created(long id, Ch_createdDto ch_createdDto) {
        Ch_created found = ch_createdRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ch_created", "id", id));

        found.setUser_name(ch_createdDto.getUser_name());
        found.setChallenge_name(ch_createdDto.getChallenge_name());
        
        
        Ch_created savedBook = ch_createdRepository.save(found);

        return mapToDto(savedBook);
    }

    @Override
    public void deleteCh_createdById(long id) {
        Ch_created found = ch_createdRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ch_comp", "id", id));
        ch_createdRepository.delete(found);
    }


}
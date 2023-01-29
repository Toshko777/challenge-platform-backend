package challenge.platform.backend.service.impl;

import challenge.platform.backend.entity.Ch_comp;
import challenge.platform.backend.exception.ResourceNotFoundException;
import challenge.platform.backend.payload.Ch_compDto;
import challenge.platform.backend.payload.Ch_compResponse;
import challenge.platform.backend.repository.Ch_compRepository;
import challenge.platform.backend.service.Ch_compService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class Ch_compServiceImpl implements Ch_compService {

    private ModelMapper modelMapper;
    private Ch_compRepository ch_compRepository;

    public Ch_compServiceImpl(Ch_compRepository ch_compRepository, ModelMapper modelMapper) {
        this.ch_compRepository = ch_compRepository;
        this.modelMapper = modelMapper;
    }

    private Ch_compDto mapToDto(Ch_comp ch_comp) {
        return modelMapper.map(ch_comp, Ch_compDto.class);
    }

    
    private Ch_comp mapToEntity(Ch_compDto ch_compDto) {
        return modelMapper.map(ch_compDto, Ch_comp.class);
    }

    @Override
    public Ch_compDto createCh_comp(Ch_compDto ch_compDto) {
        
        Ch_comp ch_compToCreate = mapToEntity(ch_compDto);
        
        Ch_comp createdCh_comp = ch_compRepository.save(ch_compToCreate);

        return mapToDto(createdCh_comp);
    }

    @Override
    public Ch_compResponse getAllCh_comps(int pageNo, int pageSize, String sortBy, String sortDir) {
        
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<Ch_comp> ch_comps = ch_compRepository.findAll(pageable);

        
        Ch_compResponse ch_compResponse = new Ch_compResponse();

        ch_compResponse.setContent(ch_comps.getContent().stream().map(ch_comp -> mapToDto(ch_comp)).toList());
        ch_compResponse.setPageNo(ch_comps.getNumber());
        ch_compResponse.setPageSize(ch_comps.getSize());
        ch_compResponse.setTotalElements(ch_comps.getTotalElements());
        ch_compResponse.setTotalPages(ch_comps.getTotalPages());
        ch_compResponse.setLast(ch_comps.isLast());

        return ch_compResponse;
    }

    @Override
    public Ch_compDto getCh_compById(long id) {
        Ch_comp ch_comp = ch_compRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ch_comp", "id", id));
        return mapToDto(ch_comp);
    }

    @Override
    public Ch_compDto updateCh_comp(long id, Ch_compDto ch_compDto) {
        Ch_comp found = ch_compRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ch_comp", "id", id));

        found.setUser_name(ch_compDto.getUser_name());
        found.setChallenge_name(ch_compDto.getChallenge_name());
        
        
        Ch_comp savedBook = ch_compRepository.save(found);

        return mapToDto(savedBook);
    }

    @Override
    public void deleteCh_compById(long id) {
        Ch_comp found = ch_compRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ch_comp", "id", id));
        ch_compRepository.delete(found);
    }


}
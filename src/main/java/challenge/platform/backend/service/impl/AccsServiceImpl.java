package challenge.platform.backend.service.impl;

import challenge.platform.backend.entity.Accs;
import challenge.platform.backend.exception.ResourceNotFoundException;
import challenge.platform.backend.payload.AccsDto;
import challenge.platform.backend.payload.AccsResponse;
import challenge.platform.backend.repository.AccsRepository;
import challenge.platform.backend.service.AccsService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AccsServiceImpl implements AccsService {

    private ModelMapper modelMapper;
    private AccsRepository accsRepository;

    public AccsServiceImpl(AccsRepository accsRepository, ModelMapper modelMapper) {
        this.accsRepository = accsRepository;
        this.modelMapper = modelMapper;
    }

    
    private AccsDto mapToDto(Accs accs) {
        return modelMapper.map(accs, AccsDto.class);
    }

    
    private Accs mapToEntity(AccsDto accsDto) {
        return modelMapper.map(accsDto, Accs.class);
    }

    @Override
    public AccsDto createAccs(AccsDto accsDto) {
       
        Accs accsToCreate = mapToEntity(accsDto);
        
        Accs createdAccs = accsRepository.save(accsToCreate);

        return mapToDto(createdAccs);
    }

    @Override
    public AccsResponse getAllAccss(int pageNo, int pageSize, String sortBy, String sortDir) {
        
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<Accs> accss = accsRepository.findAll(pageable);

        
        AccsResponse accsResponse = new AccsResponse();

        accsResponse.setContent(accss.getContent().stream().map(accs -> mapToDto(accs)).toList());
        accsResponse.setPageNo(accss.getNumber());
        accsResponse.setPageSize(accss.getSize());
        accsResponse.setTotalElements(accss.getTotalElements());
        accsResponse.setTotalPages(accss.getTotalPages());
        accsResponse.setLast(accss.isLast());

        return accsResponse;
    }

    @Override
    public AccsDto getAccsById(long id) {
        Accs accs = accsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Accs", "id", id));
        return mapToDto(accs);
    }

    @Override
    public AccsDto updateAccs(long id, AccsDto accsDto) {
        Accs found = accsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Accs", "id", id));

        found.setUser_name(accsDto.getUser_name());
        found.setClearance_lvl(accsDto.getClearance_lvl());
        
        
        Accs savedBook = accsRepository.save(found);

        return mapToDto(savedBook);
    }

    @Override
    public void deleteAccsById(long id) {
        Accs found = accsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Accs", "id", id));
        accsRepository.delete(found);
    }


}
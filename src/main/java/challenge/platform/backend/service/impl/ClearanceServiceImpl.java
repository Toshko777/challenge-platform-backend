package challenge.platform.backend.service.impl;

import challenge.platform.backend.entity.Roles;
import challenge.platform.backend.exception.ResourceNotFoundException;
import challenge.platform.backend.payload.ClearanceDto;
import challenge.platform.backend.payload.ClearanceResponse;
import challenge.platform.backend.repository.ClearanceRepository;
import challenge.platform.backend.service.ClearanceService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ClearanceServiceImpl implements ClearanceService {

    private ModelMapper modelMapper;
    private ClearanceRepository clearanceRepository;

    public ClearanceServiceImpl(ClearanceRepository clearanceRepository, ModelMapper modelMapper) {
        this.clearanceRepository = clearanceRepository;
        this.modelMapper = modelMapper;
    }

    private ClearanceDto mapToDto(Roles roles) {
        return modelMapper.map(roles, ClearanceDto.class);
    }

    
 


    @Override
    public ClearanceResponse getAllClearances(int pageNo, int pageSize, String sortBy, String sortDir) {
        
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<Roles> clearances = clearanceRepository.findAll(pageable);

        
        ClearanceResponse clearanceResponse = new ClearanceResponse();

        clearanceResponse.setContent(clearances.getContent().stream().map(roles -> mapToDto(roles)).toList());
        clearanceResponse.setPageNo(clearances.getNumber());
        clearanceResponse.setPageSize(clearances.getSize());
        clearanceResponse.setTotalElements(clearances.getTotalElements());
        clearanceResponse.setTotalPages(clearances.getTotalPages());
        clearanceResponse.setLast(clearances.isLast());

        return clearanceResponse;
    }

    @Override
    public ClearanceDto getClearanceById(long id) {
        Roles roles = clearanceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Clearance", "id", id));
        return mapToDto(roles);
    }

 

 

}
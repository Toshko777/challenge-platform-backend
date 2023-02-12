package challenge.platform.backend.service.impl;

import challenge.platform.backend.entity.AccountRole;
import challenge.platform.backend.exception.ResourceNotFoundException;
import challenge.platform.backend.payload.AccountRoleDto;
import challenge.platform.backend.payload.AccsResponse;
import challenge.platform.backend.repository.AccountsRolesRepository;
import challenge.platform.backend.service.AccountsRolesService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AccountsRolesServiceImpl implements AccountsRolesService {

    private ModelMapper modelMapper;
    private AccountsRolesRepository accountsRolesRepository;

    public AccountsRolesServiceImpl(AccountsRolesRepository accountsRolesRepository, ModelMapper modelMapper) {
        this.accountsRolesRepository = accountsRolesRepository;
        this.modelMapper = modelMapper;
    }

    
    private AccountRoleDto mapToDto(AccountRole accountRole) {
        return modelMapper.map(accountRole, AccountRoleDto.class);
    }

    
    private AccountRole mapToEntity(AccountRoleDto accountRoleDto) {
        return modelMapper.map(accountRoleDto, AccountRole.class);
    }

    @Override
    public AccountRoleDto createAccs(AccountRoleDto accountRoleDto) {
       
        AccountRole accountRoleToCreate = mapToEntity(accountRoleDto);
        
        AccountRole createdAccountRole = accountsRolesRepository.save(accountRoleToCreate);

        return mapToDto(createdAccountRole);
    }

    @Override
    public AccsResponse getAllAccss(int pageNo, int pageSize, String sortBy, String sortDir) {
        
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<AccountRole> accss = accountsRolesRepository.findAll(pageable);

        
        AccsResponse accsResponse = new AccsResponse();

        accsResponse.setContent(accss.getContent().stream().map(accountRole -> mapToDto(accountRole)).toList());
        accsResponse.setPageNo(accss.getNumber());
        accsResponse.setPageSize(accss.getSize());
        accsResponse.setTotalElements(accss.getTotalElements());
        accsResponse.setTotalPages(accss.getTotalPages());
        accsResponse.setLast(accss.isLast());

        return accsResponse;
    }

    @Override
    public AccountRoleDto getAccsById(long id) {
        AccountRole accountRole = accountsRolesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Accs", "id", id));
        return mapToDto(accountRole);
    }

    @Override
    public AccountRoleDto updateAccs(long id, AccountRoleDto accountRoleDto) {
        AccountRole found = accountsRolesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Accs", "id", id));

        found.setUsername(accountRoleDto.getUsername());
        found.setRole(accountRoleDto.getRole());
        
        
        AccountRole savedAccountRole = accountsRolesRepository.save(found);

        return mapToDto(savedAccountRole);
    }

    @Override
    public void deleteAccsById(long id) {
        AccountRole found = accountsRolesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Accs", "id", id));
        accountsRolesRepository.delete(found);
    }


}
package challenge.platform.backend.service.impl;

import challenge.platform.backend.entity.Role;
import challenge.platform.backend.exception.ResourceNotFoundException;
import challenge.platform.backend.payload.RoleDto;
import challenge.platform.backend.repository.RoleRepository;
import challenge.platform.backend.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    private ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RoleDto> getAllRoles() {
        List<Role> roleEntitiesList = roleRepository.findAll();
        List<RoleDto> roleDtosList = roleEntitiesList.stream().map(entity -> mapToDto(entity)).toList();
        return roleDtosList;
    }

    @Override
    public RoleDto getRole(Long roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleId));
        return mapToDto(role);
    }

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        Role roleToSave = mapToEntity(roleDto);
        Role saved = roleRepository.save(roleToSave);
        log.info("Role {} was created!", saved.getRole());
        return mapToDto(saved);
    }

    @Override
    public void deleteRole(Long roleId) {
        Role found = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleId));
        roleRepository.delete(found);
        log.info("Role {} was deleted!", found.getRole());

    }

    private RoleDto mapToDto(Role roleEntity) {
        return modelMapper.map(roleEntity, RoleDto.class);
    }


    private Role mapToEntity(RoleDto roleDto) {
        return modelMapper.map(roleDto, Role.class);
    }

}

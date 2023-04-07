package challenge.platform.backend.service;

import challenge.platform.backend.entity.Role;
import challenge.platform.backend.payload.RoleDto;

import java.util.List;

public interface RoleService {

    List<RoleDto> getAllRoles();
    RoleDto getRole(Long roleId);
    RoleDto createRole(RoleDto roleDto);
    void deleteRole(Long roleId);

}

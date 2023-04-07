package challenge.platform.backend.controller.admin;

import challenge.platform.backend.payload.RoleDto;
import challenge.platform.backend.service.RoleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/role")
@SecurityRequirement(
        name = "Bearer Authentication"
)
public class RoleController {

    RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<RoleDto> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{roleId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public RoleDto getRole(@PathVariable(name = "roleId") Long roleId) {
        return roleService.getRole(roleId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RoleDto> createRole(@Valid @RequestBody RoleDto dto) {
        return new ResponseEntity<>(roleService.createRole(dto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{roleId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteRole(@PathVariable(name = "roleId") Long roleId) {
        roleService.deleteRole(roleId);
    }


}

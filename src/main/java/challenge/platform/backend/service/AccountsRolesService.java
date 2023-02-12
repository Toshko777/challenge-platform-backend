package challenge.platform.backend.service;

import challenge.platform.backend.payload.AccountRoleDto;
import challenge.platform.backend.payload.AccsResponse;

public interface AccountsRolesService {

    AccountRoleDto createAccs(AccountRoleDto bookDto);

    AccsResponse getAllAccss(int pageNo, int pageSize, String sortBy, String sortDir);

    AccountRoleDto getAccsById(long id);
    

    AccountRoleDto updateAccs(long id, AccountRoleDto accountRoleDto);

    void deleteAccsById(long id);

}

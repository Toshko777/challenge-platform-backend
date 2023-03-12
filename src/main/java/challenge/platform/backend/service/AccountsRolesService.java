package challenge.platform.backend.service;

import challenge.platform.backend.payload.AccountResponse;
import challenge.platform.backend.payload.AccountRoleDto;
import challenge.platform.backend.payload.AccsResponse;

// todo ?
public interface AccountsRolesService {

    AccountRoleDto createAccs(AccountRoleDto bookDto);

    AccountResponse getAllAccss(int pageNo, int pageSize, String sortBy, String sortDir);

    AccountRoleDto getAccsById(long id);
    

    AccountRoleDto updateAccs(long id, AccountRoleDto accountRoleDto);

    void deleteAccsById(long id);

}

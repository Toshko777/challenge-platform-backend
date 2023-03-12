package challenge.platform.backend.service;

import challenge.platform.backend.payload.AccountDto;
import challenge.platform.backend.payload.AccountResponse;

public interface AccountService {

    AccountDto createUser(AccountDto bookDto);

    AccountResponse getAllUsers(int pageNo, int pageSize, String sortBy, String sortDir);

    AccountDto getUserById(long id);

    AccountDto updateUser(long id, AccountDto accountDto);

    void deleteUserById(long id);

}

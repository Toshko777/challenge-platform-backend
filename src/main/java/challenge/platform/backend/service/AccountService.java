package challenge.platform.backend.service;

import challenge.platform.backend.payload.AccountDto;
import challenge.platform.backend.payload.AccountResponse;

public interface AccountService {

    AccountDto createAccount(AccountDto bookDto);

    AccountResponse getAllAccounts(int pageNo, int pageSize, String sortBy, String sortDir);

    AccountDto getAccountById(long id);

    AccountDto getAccountByUsernameOrEmail(String username, String email);

    AccountDto updateAccount(long id, AccountDto accountDto);

    void deleteAccountById(long id);

}

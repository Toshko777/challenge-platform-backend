package challenge.platform.backend.service.impl;

import challenge.platform.backend.entity.Account;
import challenge.platform.backend.entity.AccountRole;
import challenge.platform.backend.exception.ResourceNotFoundException;
import challenge.platform.backend.payload.AccountDto;
import challenge.platform.backend.payload.AccountResponse;
import challenge.platform.backend.repository.AccountsRolesRepository;
import challenge.platform.backend.repository.AccountsRepository;
import challenge.platform.backend.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AccountServiceImpl implements AccountService {

    private ModelMapper modelMapper;
    private AccountsRepository accountsRepository;
    private AccountsRolesRepository accountsRolesRepository;

    public AccountServiceImpl(AccountsRepository accountsRepository, ModelMapper modelMapper, AccountsRolesRepository accountsRolesRepository) {
        this.accountsRepository = accountsRepository;
        this.modelMapper = modelMapper;
        this.accountsRolesRepository = accountsRolesRepository;
    }

    // convert entity to dto
    private AccountDto mapToDto(Account account) {
        return modelMapper.map(account, AccountDto.class);
    }

    // convert dto to entity
    private Account mapToEntity(AccountDto accountDto) {
        return modelMapper.map(accountDto, Account.class);
    }

    @Override
    public AccountDto createUser(AccountDto accountDto) {
        Long roleId = 0L;

        // convert dto to entity
        Account accountToSave = createUserToSave(accountDto);
        Account createdAccount = accountsRepository.save(accountToSave);

        AccountRole accountRole = createAccountRole(accountDto.getId(), roleId);
        accountsRolesRepository.save(accountRole);

        return mapToDto(createdAccount);
    }

    private AccountRole createAccountRole(Long userId, Long roleId) {
        return new AccountRole(0L, userId, roleId);
    }

    private Account createUserToSave(AccountDto accountDto) {
        Account mappedAccount = mapToEntity(accountDto);
        mappedAccount.setCreated(LocalDate.now());
        return mappedAccount;
    }

    @Override
    public AccountResponse getAllUsers(int pageNo, int pageSize, String sortBy, String sortDir) {
        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<Account> accounts = accountsRepository.findAll(pageable);

        // get content for page object
        AccountResponse accountResponse = new AccountResponse();

        accountResponse.setContent(accounts.getContent().stream().map(account -> mapToDto(account)).toList());
        accountResponse.setPageNo(accounts.getNumber());
        accountResponse.setPageSize(accounts.getSize());
        accountResponse.setTotalElements(accounts.getTotalElements());
        accountResponse.setTotalPages(accounts.getTotalPages());
        accountResponse.setLast(accounts.isLast());

        return accountResponse;
    }

    @Override
    public AccountDto getUserById(long id) {
        Account account = accountsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));
        return mapToDto(account);
    }

    @Override
    public AccountDto updateUser(long id, AccountDto accountDto) {
        Account found = accountsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));

        found.setFirstName(accountDto.getFirstName());
        found.setLastName(accountDto.getLastName());
        found.setPassword(accountDto.getPassword());
        found.setEmail(accountDto.getEmail());
        
        Account savedBook = accountsRepository.save(found);

        return mapToDto(savedBook);
    }

    @Override
    public void deleteUserById(long id) {
        Account found = accountsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        accountsRepository.delete(found);
    }


}

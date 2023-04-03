package challenge.platform.backend.service.impl;

import challenge.platform.backend.entity.Account;
import challenge.platform.backend.entity.AccountRole;
import challenge.platform.backend.exception.ResourceNotFoundException;
import challenge.platform.backend.payload.AccountDto;
import challenge.platform.backend.payload.AccountResponse;
import challenge.platform.backend.repository.AccountRepository;
import challenge.platform.backend.repository.AccountsRolesRepository;
import challenge.platform.backend.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Transactional
@Service
public class AccountServiceImpl implements AccountService {

    private ModelMapper modelMapper;
    private AccountRepository accountRepository;
    private AccountsRolesRepository accountsRolesRepository;
    private PasswordEncoder passwordEncoder;

    public AccountServiceImpl(
            AccountRepository accountRepository,
            ModelMapper modelMapper,
            AccountsRolesRepository accountsRolesRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
        this.accountsRolesRepository = accountsRolesRepository;
        this.passwordEncoder = passwordEncoder;
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
        // default is user role
        Long roleId = 1L;

        // convert dto to entity
        Account accountToSave = createUserToSave(accountDto);
        Account createdAccount = accountRepository.save(accountToSave);

        AccountRole accountRole = createAccountRole(createdAccount.getId(), roleId);
        accountsRolesRepository.save(accountRole);
        log.info("User {} with id: {} created and has role: {}", createdAccount.getUsername(), createdAccount.getId(), roleId);
        return mapToDto(createdAccount);
    }

    private AccountRole createAccountRole(Long userId, Long roleId) {
        return new AccountRole(userId, roleId);
    }

    private Account createUserToSave(AccountDto accountDto) {
        Account mappedAccount = mapToEntity(accountDto);
        mappedAccount.setCreated(LocalDate.now());
        mappedAccount.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        return mappedAccount;
    }

    @Override
    public AccountResponse getAllUsers(int pageNo, int pageSize, String sortBy, String sortDir) {
        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<Account> accounts = accountRepository.findAll(pageable);

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
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));
        return mapToDto(account);
    }

    // check if user has the rights to edit this user :)
    @Override
    public AccountDto updateUser(long id, AccountDto accountDto) {
        Account found = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));
        found.setFirstName(accountDto.getFirstName());
        found.setLastName(accountDto.getLastName());
        found.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        found.setEmail(accountDto.getEmail());

        Account savedBook = accountRepository.save(found);
        log.info("User with id: {} updated!", id);
        return mapToDto(savedBook);
    }

    @Override
    public void deleteUserById(long userId) {
        Account found = accountRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        if (found != null) {
            try {
                // todo: to remove later -> we do not need this anymore
                // because with the JoinTable annotation it happens out of the box :)
                // accountsRolesRepository.deleteByUserId(userId);
                accountRepository.delete(found);
                log.info("User with id {} was deleted.", userId);
            } catch (Exception e) {
                throw new RuntimeException("wtf");
            }
        }
    }


}

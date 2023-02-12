package challenge.platform.backend.service.impl;

import challenge.platform.backend.entity.Account;
import challenge.platform.backend.entity.AccountRole;
import challenge.platform.backend.exception.ResourceNotFoundException;
import challenge.platform.backend.payload.Roles;
import challenge.platform.backend.payload.UserDto;
import challenge.platform.backend.payload.UserResponse;
import challenge.platform.backend.repository.AccountsRolesRepository;
import challenge.platform.backend.repository.AccountsRepository;
import challenge.platform.backend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserServiceImpl implements UserService {

    private ModelMapper modelMapper;
    private AccountsRepository accountsRepository;
    private AccountsRolesRepository accountsRolesRepository;

    public UserServiceImpl(AccountsRepository accountsRepository, ModelMapper modelMapper) {
        this.accountsRepository = accountsRepository;
        this.modelMapper = modelMapper;
    }

    // convert entity to dto
    private UserDto mapToDto(Account account) {
        return modelMapper.map(account, UserDto.class);
    }

    // convert dto to entity
    private Account mapToEntity(UserDto userDto) {
        return modelMapper.map(userDto, Account.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        // convert dto to entity
        Account accountToSave = createUserToSave(userDto);
        Account createdAccount = accountsRepository.save(accountToSave);

        AccountRole accountRole = createAccountRole(userDto.getUsername());
        accountsRolesRepository.save(accountRole);

        return mapToDto(createdAccount);
    }

    private AccountRole createAccountRole(String username) {
        return AccountRole.builder().username(username).role(Roles.USER.name()).build();
    }

    private Account createUserToSave(UserDto userDto) {
        Account mappedAccount = mapToEntity(userDto);
        mappedAccount.setCreated(LocalDate.now());
        return mappedAccount;
    }

    @Override
    public UserResponse getAllUsers(int pageNo, int pageSize, String sortBy, String sortDir) {
        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<Account> users = accountsRepository.findAll(pageable);

        // get content for page object
        UserResponse userResponse = new UserResponse();

        userResponse.setContent(users.getContent().stream().map(account -> mapToDto(account)).toList());
        userResponse.setPageNo(users.getNumber());
        userResponse.setPageSize(users.getSize());
        userResponse.setTotalElements(users.getTotalElements());
        userResponse.setTotalPages(users.getTotalPages());
        userResponse.setLast(users.isLast());

        return userResponse;
    }

    @Override
    public UserDto getUserById(long id) {
        Account account = accountsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return mapToDto(account);
    }

    @Override
    public UserDto updateUser(long id, UserDto userDto) {
        Account found = accountsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        found.setFirst_name(userDto.getFirst_name());
        found.setFam_name(userDto.getFam_name());
        found.setPassword(userDto.getPassword());
        found.setEmail(userDto.getEmail());
        
        Account savedBook = accountsRepository.save(found);

        return mapToDto(savedBook);
    }

    @Override
    public void deleteUserById(long id) {
        Account found = accountsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        accountsRepository.delete(found);
    }


}

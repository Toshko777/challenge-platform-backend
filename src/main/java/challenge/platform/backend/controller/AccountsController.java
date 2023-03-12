package challenge.platform.backend.controller;

import challenge.platform.backend.payload.AccountDto;
import challenge.platform.backend.payload.AccountResponse;
import challenge.platform.backend.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// admin user-controller
@RestController
@RequestMapping("/api")
public class AccountsController {

    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "asc";

    // using interface -> because of the loose coupling
    private final AccountService accountService;

    @Autowired
    public AccountsController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/users")
    public AccountResponse getAllUsers(
            @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return accountService.getAllUsers(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDto> getUserById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(accountService.getUserById(id));
    }

    @PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDto> createUser(@Valid @RequestBody AccountDto dto) {
        return new ResponseEntity<>(accountService.createUser(dto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/user/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDto> updateUser(@PathVariable(name = "id") long id,
                                                 @Valid @RequestBody AccountDto bookDto) {
        return new ResponseEntity<>(accountService.updateUser(id, bookDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long id) {
        accountService.deleteUserById(id);
        return ResponseEntity.ok("User entity deleted successfully");
    }
}
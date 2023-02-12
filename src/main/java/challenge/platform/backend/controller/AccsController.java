package challenge.platform.backend.controller;

import challenge.platform.backend.payload.AccountRoleDto;
import challenge.platform.backend.payload.AccsResponse;
import challenge.platform.backend.service.AccountsRolesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AccsController {

    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "asc";

    // using interface -> because of the loose coupling
    private final AccountsRolesService accountsRolesService;

    @Autowired
    public AccsController(AccountsRolesService accountsRolesService) {
        this.accountsRolesService = accountsRolesService;
    }

    @GetMapping(value = "/accs")
    public AccsResponse getAllAccss(
            @RequestParam(value = "re", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return accountsRolesService.getAllAccss(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping(value = "/accs/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountRoleDto> getAccsById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(accountsRolesService.getAccsById(id));
    }


   // @GetMapping(value = "/accs/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    //public ResponseEntity<AccsDto> getAccsByUser_name(@PathVariable(name = "user_name") String user_name) {
      //  return ResponseEntity.ok(accsService.getAccsByUser_name(user_name));
    //}


    @PostMapping(value = "/accs", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountRoleDto> createAccs(@Valid @RequestBody AccountRoleDto dto) {
        return new ResponseEntity<>(accountsRolesService.createAccs(dto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/accs/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountRoleDto> updateAccs(@PathVariable(name = "id") long id,
                                                     @Valid @RequestBody AccountRoleDto bookDto) {
        return new ResponseEntity<>(accountsRolesService.updateAccs(id, bookDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/accs/{id}")
    public ResponseEntity<String> deleteAccs(@PathVariable(name = "id") Long id) {
        accountsRolesService.deleteAccsById(id);
        return ResponseEntity.ok("Book entity deleted successfully");
    }
}
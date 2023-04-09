package challenge.platform.backend.controller;


import challenge.platform.backend.payload.AccountChallengeDto;
import challenge.platform.backend.service.AccountChallengeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/challenges")
@SecurityRequirement(
        name = "Bearer Authentication"
)
public class AccountChallengeController {
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "asc";


    private final AccountChallengeService accountChallengeService;

    @Autowired
    public AccountChallengeController(AccountChallengeService accountChallengeService) {
        this.accountChallengeService = accountChallengeService;
    }


    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER', 'MODERATOR')")
    @GetMapping(value = "/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AccountChallengeDto> getAccountChallengeById(@PathVariable(name = "accountId") Long id) {
        return accountChallengeService.getAccountChallengesByAccountId(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER', 'MODERATOR')")
    @PostMapping(value = "/start", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountChallengeDto> createAccountChallenge(@Valid @RequestBody AccountChallengeDto dto) {
        return new ResponseEntity<>(accountChallengeService.createAccountChallenge(dto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER', 'MODERATOR')")
    @PutMapping(value = "/complete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountChallengeDto> updateAccountChallenge(@Valid @RequestBody AccountChallengeDto accountChallengeDto) {
        return new ResponseEntity<>(accountChallengeService.updateAccountChallenge(accountChallengeDto), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER', 'MODERATOR')")
    @DeleteMapping(value = "/abort/{id}")
    public ResponseEntity<String> deleteAccountChallenge(@PathVariable(name = "id") Long id) {
        accountChallengeService.deleteAccountChallengeById(id);
        return ResponseEntity.ok("Challenge was aborted");
    }
}

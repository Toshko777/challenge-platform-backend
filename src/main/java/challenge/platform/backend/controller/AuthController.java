package challenge.platform.backend.controller;

import challenge.platform.backend.payload.*;
import challenge.platform.backend.service.AccountService;
import challenge.platform.backend.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    AuthService authService;
    AccountService accountService;

    public AuthController(AuthService authService, AccountService accountService) {
        this.authService = authService;
        this.accountService = accountService;
    }

    // Build Login REST API
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
        log.info("{} is trying to log in", loginDto.getUsernameOrEmail());
        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        AccountDto accountDto = getAccountDto(loginDto);
        jwtAuthResponse.setUsernameOrEmail(loginDto.getUsernameOrEmail());
        jwtAuthResponse.setUserId(accountDto.getId());

        return ResponseEntity.ok(jwtAuthResponse);
    }

    // Build Register REST API
    @PostMapping("/signup")
    public ResponseEntity<RegisteredResponse> register(@RequestBody RegisterDto registerDto) {
        return new ResponseEntity<>(authService.register(registerDto), HttpStatus.CREATED);
    }

    private AccountDto getAccountDto(LoginDto loginDto) {
        AccountDto accountDto;

        if (loginDto.getUsernameOrEmail().contains("@")) {
            accountDto = accountService.getAccountByUsernameOrEmail("", loginDto.getUsernameOrEmail());
        } else {
            accountDto = accountService.getAccountByUsernameOrEmail(loginDto.getUsernameOrEmail(), "");
        }
        return accountDto;
    }
}

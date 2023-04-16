package challenge.platform.backend.service.impl;

import challenge.platform.backend.entity.Account;
import challenge.platform.backend.entity.Role;
import challenge.platform.backend.exception.ApiException;
import challenge.platform.backend.payload.LoginDto;
import challenge.platform.backend.payload.RegisterDto;
import challenge.platform.backend.payload.RegisteredResponse;
import challenge.platform.backend.repository.AccountRepository;
import challenge.platform.backend.repository.RoleRepository;
import challenge.platform.backend.security.JwtTokenProvider;
import challenge.platform.backend.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private AccountRepository accountRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager, AccountRepository accountRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        log.info("User {} successfully logged in!", loginDto.getUsernameOrEmail());

        return token;
    }

    @Override
    public RegisteredResponse register(RegisterDto registerDto) {

        // check for existing user
        if (accountRepository.existsByUsername(registerDto.getUsername())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Username already exists!");
        }

        // add check for existing email
        if (accountRepository.existsByEmail(registerDto.getEmail())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Email already exists!");
        }

        Account newAccount = new Account();
        newAccount.setUsername(registerDto.getUsername());
        newAccount.setEmail(registerDto.getEmail());
        newAccount.setFirstName(registerDto.getFirstName());
        newAccount.setLastName(registerDto.getLastName());
        newAccount.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        // todo - use the enum from package PAYLOAD class ROLE !
        Role role = roleRepository.findByRole("USER").get();
        roles.add(role);
        newAccount.setRoles(roles);

        newAccount.setCreated(LocalDate.now());

        var registered = accountRepository.save(newAccount);
        log.info("User {} registered successfully!", newAccount.getUsername());

        RegisteredResponse registeredResponse = new RegisteredResponse();
        registeredResponse.setCode("201");
        registeredResponse.setUsername(registered.getUsername());
        return registeredResponse;
    }
}

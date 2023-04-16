package challenge.platform.backend.service;

import challenge.platform.backend.payload.LoginDto;
import challenge.platform.backend.payload.RegisterDto;
import challenge.platform.backend.payload.RegisteredResponse;

public interface AuthService {

    String login(LoginDto loginDto);
    RegisteredResponse register(RegisterDto registerDto);

}

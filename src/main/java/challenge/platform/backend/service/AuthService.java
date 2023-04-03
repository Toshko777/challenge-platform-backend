package challenge.platform.backend.service;

import challenge.platform.backend.payload.LoginDto;
import challenge.platform.backend.payload.RegisterDto;

public interface AuthService {

    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);

}

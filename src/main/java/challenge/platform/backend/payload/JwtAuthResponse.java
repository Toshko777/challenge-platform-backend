package challenge.platform.backend.payload;

import lombok.Data;

@Data
public class JwtAuthResponse {

    private String accessToken;
    private String tokenType = "Bearer";
    private Long userId;
    private String usernameOrEmail;

}

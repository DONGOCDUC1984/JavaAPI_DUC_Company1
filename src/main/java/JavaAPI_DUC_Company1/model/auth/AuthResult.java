package JavaAPI_DUC_Company1.model.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResult {
    private String accessToken;
    private String refreshToken;

    public AuthResult(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}

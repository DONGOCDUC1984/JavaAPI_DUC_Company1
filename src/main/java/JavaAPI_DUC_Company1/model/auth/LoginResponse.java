package JavaAPI_DUC_Company1.model.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String accessToken;
    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}

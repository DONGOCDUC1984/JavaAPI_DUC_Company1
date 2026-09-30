package JavaAPI_DUC_Company1.model.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleLoginRequest {

    @NotBlank(message = "Token is required")
    private String token;
}

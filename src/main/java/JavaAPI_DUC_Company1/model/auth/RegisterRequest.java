package JavaAPI_DUC_Company1.model.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Pattern( regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter" )
    @Pattern( regexp = ".*[a-z].*", message = "Password must contain at least one lowercase letter" )
    @Pattern( regexp = ".*\\d.*", message = "Password must contain at least one number" )
    @Pattern( regexp = ".*[^A-Za-z\\d].*", message = "Password must have at least one special character" )
    private String password;

}

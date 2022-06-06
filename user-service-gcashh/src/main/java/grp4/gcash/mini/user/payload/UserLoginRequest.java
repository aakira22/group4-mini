package grp4.gcash.mini.user.payload;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class UserLoginRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}

package grp4.gcash.mini.user.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserLoginResponse {
    private String mobileNumber;
    private LocalDateTime lastLoggedIn;
}

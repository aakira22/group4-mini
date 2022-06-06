package con.tbs.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserLoginResponse {
    private String userId;
    private LocalDateTime lastLoggedIn;

    public UserLoginResponse() {
    }
}


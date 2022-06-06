package con.tbs.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserLoginResponse {
    private String userId;
    private LocalDateTime lastLoggedIn;

<<<<<<< HEAD
    public UserLoginResponse(String userId, LocalDateTime lastLoggedIn) {
        this.userId = userId;
        this.lastLoggedIn = lastLoggedIn;
=======
    public UserLoginResponse() {
>>>>>>> ecd2d82b5b1ef81a15a823684c001508036b7e1c
    }
}


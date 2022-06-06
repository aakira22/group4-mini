package con.tbs.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDetails {
    private String mobileNumber;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;

    public UserDetails(String mobileNumber, String email, String firstName, String lastName, LocalDateTime createdAt) {
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
    }

    public UserDetails() {
    }
}

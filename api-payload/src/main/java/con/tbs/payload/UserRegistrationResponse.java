package con.tbs.payload;

import lombok.Data;

@Data
public class UserRegistrationResponse {
    private String mobileNumber;

    public UserRegistrationResponse(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public UserRegistrationResponse() {
    }
}


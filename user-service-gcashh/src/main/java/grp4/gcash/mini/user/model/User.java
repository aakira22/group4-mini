package grp4.gcash.mini.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class User {

    private String firstName;
	private String lastName;
    private String middleName;
    private String email;

    @Id
	private String mobileNumber;

    private String password;
    private LocalDateTime lastLoggedIn;
    private LocalDateTime dateCreated;


    @PrePersist
    public void setPreData() {
        LocalDateTime now = LocalDateTime.now();
        dateCreated = now;
        lastLoggedIn = now;
    }

    public User(String firstName, String lastName, String middleName, String email, String mobileNumber, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.password = password;
    }
}

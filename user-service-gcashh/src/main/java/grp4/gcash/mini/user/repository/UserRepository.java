package grp4.gcash.mini.user.repository;

import grp4.gcash.mini.user.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    boolean existsByEmail(String email);
    boolean existsByMobileNumber(String mobileNumber);
    User getUserByMobileNumber(String mobileNumber);
    User getUserByEmail(String email);
}

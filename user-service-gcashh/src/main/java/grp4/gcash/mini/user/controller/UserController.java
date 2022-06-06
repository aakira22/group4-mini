package grp4.gcash.mini.user.controller;

import con.tbs.payload.*;
import grp4.gcash.mini.user.model.User;
import grp4.gcash.mini.user.exception.*;
import grp4.gcash.mini.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RestTemplate restTemplate;

    //private final String activityServiceEndpoint;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.restTemplate = restTemplate;
    }

    @PostMapping
    public UserRegistrationResponse register(@Valid @RequestBody UserRegistrationRequest request) throws UserRegistrationException {

        if(userRepository.existsByEmail(request.getEmail())){
            throw new UserRegistrationException("User already registered");
        }

        User user = new User(request.getFirstName(), request.getLastName(), request.getMiddleName(), request.getEmail(), request.getMobileNumber(), passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);

        return new UserRegistrationResponse(savedUser.getMobileNumber());
    }

    @PostMapping("login")
    public UserLoginResponse login(@Valid @RequestBody UserLoginRequest request) throws UserNotFoundException {
        if(!userRepository.existsByEmail(request.getEmail())){
            throw new UserNotFoundException("User not found");
        }

        User userByEmail = userRepository.getUserByEmail(request.getEmail());
        if (passwordEncoder.matches(request.getPassword(), userByEmail.getPassword())) {
            userByEmail.setLastLoggedIn(LocalDateTime.now());

            User savedUser = userRepository.save(userByEmail);

            UserLoginResponse response = new UserLoginResponse();
            response.setMobileNumber(savedUser.getMobileNumber());
            response.setLastLoggedIn(savedUser.getLastLoggedIn());

            return response;

        }
        throw new UserLoginException("Incorrect password");
    }

    @GetMapping("all")
    public GetAllUsersResponse getAllUsers(){
        //TODO
    }

    @ExceptionHandler(UserRegistrationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleUserRegistrationException(UserRegistrationException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler(UserLoginException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleUserAuthenticationException(UserLoginException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleUserNotFoundException(UserNotFoundException e) {
        return Map.of("error", e.getMessage());
    }
}

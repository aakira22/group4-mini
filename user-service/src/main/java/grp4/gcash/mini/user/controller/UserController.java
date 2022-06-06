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
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

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

        if (userRepository.existsById(request.getUserId())) {
            throw new UserRegistrationException("User already registered!");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserRegistrationException("Email not available!");
        }

        User user = new User(request.getUserId(), request.getFirstName(),
                request.getLastName(),
                request.getMiddleName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        return new UserRegistrationResponse(savedUser.getUserId());
    }

    @GetMapping("{id}")
    public GetUserResponse getUser(@PathVariable String id) throws UserNotFoundException {
        User user = getThisUser(id);
        GetUserResponse response = new GetUserResponse(user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getMiddleName(),
                user.getEmail(),
                user.getPassword());

        return response;
    }

    @PostMapping("login/{id}")
    public void userLogin(@PathVariable String id) throws UserNotFoundException {
        User user = getThisUser(id);
        user.setLastLoggedIn(LocalDateTime.now());
        userRepository.save(user);
    }


    @GetMapping("all")
    public GetAllUsersResponse getAllUsers() {
        GetAllUsersResponse response = new GetAllUsersResponse(userRepository.count(), new ArrayList<>());
        userRepository.findAll().forEach(user -> response.getUsers().add(new UserDetails(user.getUserId(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getDateCreated())));

        return response;
    }

    public User getThisUser(String userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User Not Found");
        }
        return user.get();
    }

    @ExceptionHandler(UserRegistrationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleUserRegistrationException(UserRegistrationException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleUserNotFoundException(UserNotFoundException e) {
        return Map.of("error", e.getMessage());
    }
}

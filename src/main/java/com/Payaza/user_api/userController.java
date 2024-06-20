package com.Payaza.user_api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/api")
@Validated
public class userController {

    @Autowired
    private userService userService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{userId}")
    public Optional<User> getUser(@PathVariable int userId) {
        Optional<User> theUser = userService.getUserById(userId);
        if (theUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id - " + userId);
        }
        return theUser;
    }

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) {
        user.setId(0);
        return userService.saveUser(user);
    }

    @PutMapping("/users/{userId}")
    public User updateUser(@PathVariable int userId, @Valid @RequestBody User updatedUser) {
        Optional<User> existingUser = userService.getUserById(userId);
        if (existingUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id - " + userId);
        }
        User userToUpdate = existingUser.get();
        userToUpdate.setFirstName(updatedUser.getFirstName());
        userToUpdate.setLastName(updatedUser.getLastName());
        userToUpdate.setEmail(updatedUser.getEmail());


        return userService.saveUser(userToUpdate);
    }

    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable int userId) {
        Optional<User> userToDelete = userService.getUserById(userId);
        if (userToDelete.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id - " + userId);
        }
        userService.deleteUser(userId);
        return "Deleted user with id " + userId;
    }

    @PatchMapping("/users/{userId}")
    public User patchUser(@PathVariable int userId, @RequestBody Map<String, Object> updates) {
        Optional<User> existingUser = userService.getUserById(userId);
        if (existingUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id - " + userId);
        }
        User userToUpdate = existingUser.get();

        if (updates.containsKey("firstName")) {
            userToUpdate.setFirstName((String) updates.get("firstName"));
        }
        if (updates.containsKey("lastName")) {
            userToUpdate.setLastName((String) updates.get("lastName"));
        }
        if (updates.containsKey("email")) {
            userToUpdate.setEmail((String) updates.get("email"));
        }


        return userService.saveUser(userToUpdate);
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> headUser(@PathVariable int userId) {
        Optional<User> existingUser = userService.getUserById(userId);
        if (existingUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setCacheControl("max-age=3600, must-revalidate");
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> optionsUser(@PathVariable int userId) {
        Optional<User> existingUser = userService.getUserById(userId);
        if (existingUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        Set<HttpMethod> allowedMethods = new HashSet<>();
        allowedMethods.add(HttpMethod.GET);
        allowedMethods.add(HttpMethod.HEAD);
        allowedMethods.add(HttpMethod.POST);
        allowedMethods.add(HttpMethod.PUT);
        allowedMethods.add(HttpMethod.DELETE);
        allowedMethods.add(HttpMethod.OPTIONS);
        allowedMethods.add(HttpMethod.PATCH);

        headers.setAllow(allowedMethods);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}

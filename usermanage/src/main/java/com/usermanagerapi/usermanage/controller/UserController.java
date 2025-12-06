package com.usermanagerapi.usermanage.controller;

import com.usermanagerapi.usermanage.entity.User;
import com.usermanagerapi.usermanage.service.UserService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


// Post user
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto) {
        var userId = userService.createUser(createUserDto);
        return ResponseEntity.created(URI.create("/user/" + userId.toString())).build();
    }

    // GET by id
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId) {
        var user = userService.getUserById(userId);
        // Validação de usuario presente, se sim, retorna 200, se não, retorna 404.
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //GET all
    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        var users = userService.listUsers();

        return ResponseEntity.ok(users);
    }

    // UPDATE
    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUserById(@PathVariable("userId") String userId, @RequestBody UpdateUserDto updateUserDto) {
        userService.updateUserById(userId, updateUserDto);

        return ResponseEntity.noContent().build();
    }

    // DELETE
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable("userId") String userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
}

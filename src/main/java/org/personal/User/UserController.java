package org.personal.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.personal.User.dto.UserDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getAll(){
        log.info("Fetching all users");
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public UserDto getById(@PathVariable Long userId){
        log.info("Fetching user with ID: {}", userId);
        return userService.getUserById(userId);
    }
    @GetMapping("/e/{email}")
    public UserDto getByEmail(@PathVariable String email){
        log.info("Fetching user with email: {}", email);
        return userService.getUserByEmail(email);
    }

    @PostMapping
    public UserDto add(@RequestBody UserDto userDto){
        log.info("Adding a new user");
        return userService.addUser(userDto);
    }

    @PatchMapping("/{userId}")
    public UserDto update(@PathVariable Long userId, @RequestBody UserDto userDto){
        log.info("Updating user with ID: {}", userId);
        return userService.updateUser(userId, userDto);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId){
        log.info("Deleting user with ID: {}", userId);
        userService.deleteUser(userId);
    }
}

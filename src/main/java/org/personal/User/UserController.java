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
        log.debug("Fetching all users");
        return userService.getAll();
    }

    @GetMapping("/{userId}")
    public UserDto getById(@PathVariable Long userId){
        log.debug("Fetching user with ID: {}", userId);
        return userService.getById(userId);
    }

    @PostMapping
    public UserDto add(@RequestBody UserDto userDto){
        log.debug("Adding a new user");
        return userService.add(userDto);
    }

    @PatchMapping("/{userId}")
    public UserDto update(@PathVariable Long userId, @RequestBody UserDto userDto){
        log.debug("Updating user with ID: {}", userId);
        return userService.update(userId, userDto);
    }

    @DeleteMapping("/{userId}")
    public UserDto delete(@PathVariable Long userId){
        log.debug("Deleting user with ID: {}", userId);
        return userService.delete(userId);
    }


}

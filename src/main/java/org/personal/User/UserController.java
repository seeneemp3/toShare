package org.personal.User;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }
    @GetMapping("/{userId}")
    public User getById(@PathVariable Long userId){
        return userService.getById(userId);
    }

    @PostMapping
    public User add(@RequestBody User user){
        return userService.add(user);
    }
    @DeleteMapping("/{userId}")
    public User delete(@PathVariable Long userId){
      return userService.delete(userId);
    }

}

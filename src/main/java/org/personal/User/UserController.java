package org.personal.User;

import lombok.RequiredArgsConstructor;
import org.personal.User.dto.UserDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getAll(){
        return userService.getAll();
    }
    @GetMapping("/{userId}")
    public UserDto getById(@PathVariable Long userId){
        return userService.getById(userId);
    }
    @PostMapping
    public UserDto add(@RequestBody UserDto userDto){
        return userService.add(userDto);
    }
    @PatchMapping("/{userId}")
    public UserDto update(@PathVariable Long userId, @RequestBody UserDto userDto){
        return userService.update(userId, userDto);
    }
    @DeleteMapping("/{userId}")
    public UserDto delete(@PathVariable Long userId){
      return userService.delete(userId);
    }

}

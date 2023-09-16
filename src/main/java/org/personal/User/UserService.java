package org.personal.User;

import org.personal.User.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(Long userId);
    UserDto getUserByEmail(String email);
    UserDto addUser(UserDto userDto);
    UserDto updateUser(Long userId, UserDto userDto);
    void deleteUser(Long userId);
}

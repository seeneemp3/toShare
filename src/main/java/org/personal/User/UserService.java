package org.personal.User;

import org.personal.User.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAll();
    UserDto getById(Long userId);
    UserDto add(UserDto userDto);
    UserDto update(Long userId, UserDto userDto);
    UserDto delete(Long userId);
}

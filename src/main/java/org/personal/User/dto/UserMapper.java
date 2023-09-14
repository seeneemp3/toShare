package org.personal.User.dto;
import org.personal.User.User;

public interface UserMapper {

    User dtoToUser(UserDto userDto);

    UserDto userToDto(User user);
}

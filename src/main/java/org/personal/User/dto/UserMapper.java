package org.personal.User.dto;
import org.personal.User.User;

public interface UserMapper {

    User fromDto(UserDto userDto);

    UserDto toDto(User user);
}

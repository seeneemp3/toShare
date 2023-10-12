package org.personal.user.dto;
import org.personal.user.User;

public interface UserMapper {

    User fromDto(UserDto userDto);

    UserDto toDto(User user);
}

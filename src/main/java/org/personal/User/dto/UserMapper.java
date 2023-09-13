package org.personal.User.dto;
import org.mapstruct.Mapper;
import org.personal.User.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User dtoToUser(UserDto userDto);

    UserDto userToDto(User user);
}

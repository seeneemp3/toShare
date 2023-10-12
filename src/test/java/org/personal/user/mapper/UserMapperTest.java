package org.personal.user.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.personal.user.User;
import org.personal.user.dto.UserDto;
import org.personal.user.dto.UserMapperImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {

    private UserMapperImpl mapper;

    @BeforeEach
    public void setup() {
        mapper = new UserMapperImpl();
    }

    @Test
    public void testToDto() {
        User user = User.builder()
                .id(1L)
                .name("org/personal/user")
                .email("user@user.com")
                .build();

        UserDto userDto = mapper.toDto(user);

        assertEquals(1L, userDto.getId());
        assertEquals("org/personal/user", userDto.getName());
        assertEquals("user@user.com", userDto.getEmail());
    }

    @Test
    public void testFromDto() {
        UserDto userDto = new UserDto(1L,"user@user.com", "org/personal/user");

        User user = mapper.fromDto(userDto);

        assertEquals(1L, user.getId());
        assertEquals("org/personal/user", user.getName());
        assertEquals("user@user.com", user.getEmail());
    }
}

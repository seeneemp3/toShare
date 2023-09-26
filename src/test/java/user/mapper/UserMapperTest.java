package user.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.personal.User.User;
import org.personal.User.dto.UserDto;
import org.personal.User.dto.UserMapperImpl;

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
                .name("user")
                .email("user@user.com")
                .build();

        UserDto userDto = mapper.toDto(user);

        assertEquals(1L, userDto.getId());
        assertEquals("user", userDto.getName());
        assertEquals("user@user.com", userDto.getEmail());
    }

    @Test
    public void testFromDto() {
        UserDto userDto = new UserDto(1L,"user@user.com", "user");

        User user = mapper.fromDto(userDto);

        assertEquals(1L, user.getId());
        assertEquals("user", user.getName());
        assertEquals("user@user.com", user.getEmail());
    }
}

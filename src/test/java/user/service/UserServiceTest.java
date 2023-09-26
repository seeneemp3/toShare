package user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personal.User.User;
import org.personal.User.UserRepository;
import org.personal.User.UserServiceImpl;
import org.personal.User.dto.UserDto;
import org.personal.User.dto.UserMapper;
import org.personal.User.dto.UserMapperImpl;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository repository;
    @Mock
    private UserMapperImpl mapper;
    @InjectMocks
    private UserServiceImpl service;
    private User user;
    @BeforeEach
    public void setup(){
        user = User.builder()
                .id(1L)
                .name("user")
                .email("user@user.com")
                .build();

        when(mapper.toDto(any(User.class)))
                .thenReturn(new UserDto(1L, "user", "user@user.com"));
    }

    @Test
    public void getAll(){
        when(repository.findAll())
                .thenReturn(List.of(user));

        assertEquals(service.getAllUsers(), List.of(mapper.toDto(user)));
    }




}

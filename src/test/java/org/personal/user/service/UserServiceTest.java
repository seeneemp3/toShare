package org.personal.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.personal.User.User;
import org.personal.User.UserRepository;
import org.personal.User.UserServiceImpl;
import org.personal.User.dto.UserDto;
import org.personal.User.dto.UserMapperImpl;
import org.personal.exeption.UserAlreadyExistsException;
import org.personal.exeption.UserNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository repository;
    @Mock
    private UserMapperImpl mapper;
    @InjectMocks
    private UserServiceImpl service;
    private User user;
    private UserDto userDto;

    @BeforeEach
    public void setup() {
        userDto = new UserDto(1L, "user", "user@user.com");
        user = User.builder()
                .id(1L)
                .name("org/personal/user")
                .email("user@user.com")
                .build();

        when(mapper.toDto(user))
                .thenReturn(userDto);

        when(mapper.fromDto(userDto))
                .thenReturn(user);
    }

    @Test
    public void getAllUsers() {
        when(repository.findAll())
                .thenReturn(List.of(user));
        assertEquals(service.getAllUsers(), List.of(mapper.toDto(user)));
    }

    @Test
    public void getUserById() {
        when(repository.findById(anyLong()))
                .thenReturn(Optional.of(user));
        assertEquals(service.getUserById(1L), mapper.toDto(user));

        when((repository.findById(anyLong()))).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> service.getUserById(10L));
    }

    @Test
    public void addUser() {
        when(repository.getByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(repository.save(user)).thenReturn(user);

        assertEquals(userDto, service.addUser(userDto));
    }

    @Test
    public void addUser_AlreadyExists() {
        when(repository.getByEmail(userDto.getEmail())).thenReturn(Optional.of(user));
        assertThrows(UserAlreadyExistsException.class, () -> service.addUser(userDto));
        verify(repository, never()).save(any(User.class));
    }

    @Test
    void updateUser() {
        userDto = new UserDto(
                1L,
                "update",
                "update@email.com");

        when(repository.findById(anyLong())).thenReturn(Optional.of(user));
        when(repository.save(any())).thenReturn(user);
        assertEquals(service.updateUser(1L, userDto), mapper.toDto(user));

        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> service.updateUser(1L, userDto));
    }

    @Test
    public void deleteUser_NotExists() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> service.deleteUser(10L));
    }

    @Test
    public void deleteUser() {
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        service.deleteUser(1L);
        verify(repository).delete(user);
    }

    @Test
    public void getUserByEmail() {
        when(repository.getByEmail(anyString())).thenReturn(Optional.of(user));
        assertEquals(service.getUserByEmail("user@user.com"), mapper.toDto(user));
    }

    @Test
    public void getUserByEmail_NotExists() {
        when(repository.getByEmail(anyString())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> service.getUserByEmail("getByEmail"));
    }

}

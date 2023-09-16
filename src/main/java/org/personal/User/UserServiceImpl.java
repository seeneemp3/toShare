package org.personal.User;

import lombok.RequiredArgsConstructor;
import org.personal.User.dto.UserDto;
import org.personal.User.dto.UserMapper;
import org.personal.exeption.UserAlreadyExistsException;
import org.personal.exeption.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::userToDto).collect(Collectors.toList());
    }

    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID = " + userId + " not found"));
        return userMapper.userToDto(user);
    }

    public UserDto getUserByEmail(String email) {
        User u = (User) userRepository.getByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email = " + email + " not found"));
        return userMapper.userToDto(u);
    }

    public UserDto addUser(UserDto userDto) {
        userRepository.getByEmail(userDto.getEmail()).ifPresent(user -> {
            throw new UserAlreadyExistsException("User with email = " + userDto.getEmail() + " already exist");
        });
        User user = userMapper.dtoToUser(userDto);
        return userMapper.userToDto(userRepository.save(user));
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("user with ID = " + id + " not found"));
        if (userDto.getEmail() != null) {
            emailDuplicationCheck(userDto.getEmail());
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getName() != null && !userDto.getName().isEmpty() && !userDto.getName().isBlank()) {
            user.setName(userDto.getName());
        }
        return userMapper.userToDto(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("user ID = " + id + " not found"));
        userRepository.delete(user);
    }

    private void emailDuplicationCheck(String email) {
        userRepository.getByEmail(email).ifPresent(user -> {
            throw new UserAlreadyExistsException("User with email = " + email + " already exist");
        });
    }


}

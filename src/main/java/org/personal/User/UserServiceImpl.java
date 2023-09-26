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
        return userRepository.findAll().stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    public UserDto getUserById(Long userId) {
        return userMapper.toDto(getUser(userId));
    }

    public UserDto getUserByEmail(String email) {
        User u = (User) userRepository.getByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email = " + email + " not found"));
        return userMapper.toDto(u);
    }

    public UserDto addUser(UserDto userDto) {
        emailDuplicationCheck(userDto.getEmail());
        User user = userMapper.fromDto(userDto);
        return userMapper.toDto(userRepository.save(user));
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        User user = getUser(id);
        if (userDto.getEmail() != null) {
            emailDuplicationCheck(userDto.getEmail());
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getName() != null && !userDto.getName().isEmpty() && !userDto.getName().isBlank()) {
            user.setName(userDto.getName());
        }
        return userMapper.toDto(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        userRepository.delete(getUser(id));
    }

    private void emailDuplicationCheck(String email) {
        userRepository.getByEmail(email).ifPresent(user -> {
            throw new UserAlreadyExistsException("User with email = " + email + " already exist");
        });
    }
    private User getUser(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID = " + userId + " not found"));
    }


}

package org.personal.User;

import lombok.RequiredArgsConstructor;
import org.personal.User.dto.UserDto;
import org.personal.User.dto.UserMapper;
import org.personal.exeption.UserAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getAll() {
        return userRepository.getAll().stream().map(userMapper::userToDto).collect(Collectors.toList());
    }
    public UserDto getById(Long userId){
        return userMapper.userToDto(userRepository.getById(userId));
    }
    public UserDto add(UserDto userDto) {
        User user = userMapper.dtoToUser(userDto);
        return userMapper.userToDto(userRepository.add(user));
    }
    public UserDto update(Long userId, UserDto userDto){
        User user = userRepository.getById(userId);
        if (userDto.getEmail() != null){
            userRepository.getAll().stream().map(User::getEmail).filter(u -> userDto.getEmail().equals(u))
                    .findAny()
                    .orElseThrow(() -> new UserAlreadyExistsException("user with email " + userDto.getEmail() + " already exists"));
            user.setEmail(user.getEmail());
        }
        if(userDto.getName() != null){
            user.setName(user.getName());
        }
        return userMapper.userToDto(userRepository.update(userId, user));
    }
    public UserDto delete(Long userId){
        return userMapper.userToDto(userRepository.delete(userId));
    }

}

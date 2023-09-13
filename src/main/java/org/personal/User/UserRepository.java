package org.personal.User;

import org.personal.User.dto.UserDto;

import java.util.List;

public interface UserRepository {
    List<User> getAll();
    User getById(Long userId);
    User add(User user);
    User update(Long userId, User user);
    User delete(Long userId);

}

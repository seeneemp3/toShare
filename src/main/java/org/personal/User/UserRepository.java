package org.personal.User;

import java.util.List;

public interface UserRepository {
    List<User> getAll();
    User add(User user);
    User getById(Long userId);
    User delete(Long userId);

}

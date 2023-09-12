package org.personal.User;

import java.util.List;

public interface UserRepository {
    List<User> getAll();
    User save(User user);
}

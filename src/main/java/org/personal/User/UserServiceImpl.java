package org.personal.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository repository;

    public List<User> getAll() {
        return repository.getAll();
    }

    public User save(User user) {
        return repository.save(user);
    }

}

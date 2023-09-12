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

    public User add(User user) {
        return repository.add(user);
    }
    public User getById(Long userId){ return repository.getById(userId);}
    public User delete(Long userId){ return repository.delete(userId);}

}

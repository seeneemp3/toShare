package org.personal.User;

import org.personal.Item.Item;
import org.personal.exeption.InvalidInputDataException;
import org.personal.exeption.UserAlreadyExistsException;
import org.personal.exeption.UserNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component("InMemUserRepository")
public class InMemUserRepository implements UserRepository{
    private Map<Long, User> users = new HashMap<>();;
    private Long currentId;

    @Override
    public List<User> getAll() {
        return users.values().stream().toList();
    }

    @Override
    public User add(User user) {
        if (users.values().stream().map(User::getEmail).noneMatch(u -> u.equals(user.getEmail()))) {
            validateUser(user);
            if(user.getId() == null){ user.setId(getId());}
            users.put(user.getId(), user);
        }else throw new UserAlreadyExistsException("User with E-mail=" + user.getEmail() + " already exists!");
        return user;
    }

    public User getById(Long userId) {
        if (!users.containsKey(userId)) {
            throw new UserNotFoundException("User with ID=" + userId + " not found!");
        }
        return users.get(userId);
    }

    public User delete(Long userId){
        if (!users.containsKey(userId)) {
            throw new UserNotFoundException("User with ID=" + userId + " not found!");
        }
        return users.remove(userId);
    }





    private void validateUser(User user){
        if (!user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
            throw new InvalidInputDataException("Incorrect user e-mail: " + user.getEmail());
        }
    }

    private long getId() {
        long lastId = users.values().stream()
                .mapToLong(User::getId)
                .max()
                .orElse(0);
        return lastId + 1;
    }
}

package org.personal.User;

import org.personal.User.User;
import org.personal.User.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<Object> getByEmail(String email);

}

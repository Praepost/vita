package org.service.web.user.entity.repository;

import org.service.web.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    User getUserByUsername(String username);
    Boolean existsByUsername(String name);
    User findUserByUsername(String username);
    List<User> findUsersByUsernameContainingIgnoreCase(String username);
    List<User> findUserByActive(Boolean active, Pageable pageable);
    List<User> findByUsernameContaining(String username);

}

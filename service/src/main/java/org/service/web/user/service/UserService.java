package org.service.web.user.service;

import lombok.RequiredArgsConstructor;
import org.service.web.user.controller.dto.OperatorRequst;
import org.service.web.user.controller.dto.RegistrationRequest;
import org.service.web.user.controller.dto.SuccessResponse;
import org.service.web.user.controller.dto.UsersResponse;
import org.service.web.user.entity.Role;
import org.service.web.user.entity.User;
import org.service.web.user.entity.repository.RoleRepository;
import org.service.web.user.entity.repository.UserRepo;
import org.service.web.user.exception.UserRegisterException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RoleRepository roleRepo;
    private final UserRepo userRepo;

    public SuccessResponse registration(String username, String password,
            String role) {
        if (userRepo.existsByUsername(username)) {
            throw new UserRegisterException("Пользователь существет");
        }

        User user = new User();

        user.setUsername(username);
        user.setPassword(username);

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepo.findByName("Пользователь"));
        user.setRoles(roles);

        userRepo.save(user);

        return new SuccessResponse("Пользователь успешно зарегестрирован");
    }

    public SuccessResponse operator(String username) {

        Set<Role> roles = new HashSet<>();

        User user = userRepo.findUserByUsername(username);
        roles = user.getRoles();

        Role role = roleRepo.findByName("Оператор");
        roles.add(role);

        user.setRoles(roles);

        return new SuccessResponse("Роль успешно обновленна");
    }

    public UsersResponse users() {
        List<User> users = userRepo.findAll();

        return new UsersResponse(users);
    }

    public UsersResponse usersContaining(String username) {
        List<User> users = userRepo.findUsersByUsernameContainingIgnoreCase(username);
        return new UsersResponse(users);

    }
}

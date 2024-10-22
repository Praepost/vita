package org.service.web.user.controller;

import lombok.RequiredArgsConstructor;
import org.service.web.user.controller.dto.OperatorRequst;
import org.service.web.user.controller.dto.RegistrationRequest;
import org.service.web.user.controller.dto.SuccessResponse;
import org.service.web.user.controller.dto.UsersResponse;
import org.service.web.user.entity.Role;
import org.service.web.user.entity.User;
import org.service.web.user.exception.UserRegisterException;
import org.service.web.user.service.UserService;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class UserController implements IUserController{
    private final UserService service;

    @Override
    public SuccessResponse registration(RegistrationRequest request) {
        return service.registration(request.getUsername(), request.getPasswrod(),
                request.getRole());
    }

    @Override
    public SuccessResponse operator(OperatorRequst request) {
        return service.operator(request.getUsername());
    }

    @Override
    public UsersResponse users() {
        return service.users();
    }

    @Override
    public UsersResponse usersContaining(String username) {
        return service.usersContaining(username);
    }
}
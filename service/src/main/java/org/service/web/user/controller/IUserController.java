package org.service.web.user.controller;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.service.web.exception.dto.MessagesResponse;
import org.service.web.exception.dto.SuccessfulResponse;
import org.service.web.user.controller.dto.OperatorRequst;
import org.service.web.user.controller.dto.RegistrationRequest;
import org.service.web.user.controller.dto.SuccessResponse;
import org.service.web.user.controller.dto.UsersResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Validated
public interface IUserController {
    @Transactional
    @PostMapping("/registration/")
    @ApiResponses({
            @ApiResponse(code = 200, response = SuccessfulResponse.class, message =
                    "Успешный ответ: "),
            @ApiResponse(code = 400,  response = MessagesResponse.class, message =
                    "Ошибка при регистрации "),
    })
    SuccessResponse registration(@Valid @RequestBody RegistrationRequest request);

    @PreAuthorize("hasRole('Администратор')")
    @Transactional
    @PostMapping("/operator/")
    @ApiResponses({
            @ApiResponse(code = 200, response = SuccessfulResponse.class, message =
                    "Успешный ответ: "),
            @ApiResponse(code = 400,  response = MessagesResponse.class, message =
                    "Ошибка при установки прав "),
    })
    SuccessResponse operator(@Valid @RequestBody OperatorRequst request);

    @PreAuthorize("hasRole('Администратор')")
    @GetMapping("/users/")
//    @ApiOperation(value = "Authenticate user by telephone",
//            authorizations = {@Authorization(value = "Jwt token")})
    @ApiResponses({
            @ApiResponse(code = 200, response = SuccessfulResponse.class, message =
                    "Успешный ответ: "),
            @ApiResponse(code = 400, message =
                    "Ошибка при просмотре пользователей: ")
    })
    UsersResponse users();

    @PreAuthorize("hasRole('Администратор')")
    @GetMapping("/users/{username}")
//    @ApiOperation(value = "Authenticate user by telephone",
//            authorizations = {@Authorization(value = "Jwt token")})
    @ApiResponses({
            @ApiResponse(code = 200, response = SuccessfulResponse.class, message =
                    "Успешный ответ: "),
            @ApiResponse(code = 400, message =
                    "Ошибка при просмотре пользователей: ")
    })
    UsersResponse usersContaining(@Valid @PathVariable
            (name = "username") String username);
}
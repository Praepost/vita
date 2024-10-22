package org.service.web.task.controller;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.service.web.exception.dto.MessagesResponse;
import org.service.web.exception.dto.SuccessfulResponse;
import org.service.web.task.controller.dto.*;
import org.service.web.user.controller.dto.SuccessResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Validated
public interface ITaskController {
    @PreAuthorize("hasRole('Пользователь')")
    @Transactional
    @PostMapping("/")
    @ApiResponses({
            @ApiResponse(code = 200, response = SuccessfulResponse.class, message =
                    "Успешный ответ: "),
            @ApiResponse(code = 400,  response = MessagesResponse.class, message =
                    "Ошибка при создании "),
    })
    @ApiModelProperty(
            value = "first name of the user",
            name = "firstName",
            dataType = "String",
            example = "Vatsal")
    SuccessResponse registration(@Valid @RequestBody CreateTaskRequest request);

    @PreAuthorize("hasRole('Пользователь')")
    @Transactional
    @PostMapping("/posts/{id}")
    @ApiResponses({
            @ApiResponse(code = 200, response = SuccessfulResponse.class, message =
                    "Успешный ответ: "),
            @ApiResponse(code = 400,  response = MessagesResponse.class, message =
                    "Ошибка при отправке "),
    })
    SuccessResponse storage(@Valid @RequestBody StorageRequest request,
                            @Valid @PathVariable(name = "id") String id);

    @PreAuthorize("hasRole('Пользователь')")
    @Transactional
    @PutMapping("/edit/{id}")
    @ApiResponses({
            @ApiResponse(code = 200, response = SuccessfulResponse.class, message =
                    "Успешный ответ: "),
            @ApiResponse(code = 400,  response = MessagesResponse.class, message =
                    "Ошибка при редактировании "),
    })
    SuccessResponse edit(@Valid @RequestBody EditRequest request,
                         @Valid @PathVariable(name = "id") String id);

//    @PreAuthorize("hasAnyRole('Пользователь', 'Администратор')")

    @PreAuthorize("hasAnyRole('Пользователь', 'Оператор')")
    @Transactional
            @PostMapping("/look/{id}")
    @ApiResponses({
            @ApiResponse(code = 200, response = SuccessfulResponse.class, message =
                    "Успешный ответ: "),
            @ApiResponse(code = 400,  response = MessagesResponse.class, message =
                    "Ошибка при просмотре "),
    })
    TaskResponse lookAthor(@Valid @PathVariable String id);

    @PreAuthorize("hasAnyRole('Пользователь', 'Оператор')")
    @Transactional
    @PostMapping("/look/author/")
    @ApiResponses({
            @ApiResponse(code = 200, response = SuccessfulResponse.class, message =
                    "Успешный ответ: "),
            @ApiResponse(code = 400,  response = MessagesResponse.class, message =
                    "Ошибка при просмотре "),
    })
    TasksResponse lookAuthor(@Valid @RequestBody TasksRequest request);

    @PreAuthorize("hasRole('Оператор')")
    @Transactional
    @GetMapping("/tasks/")
    @ApiResponses({
            @ApiResponse(code = 200, response = SuccessfulResponse.class, message =
                    "Успешный ответ: "),
            @ApiResponse(code = 400,  response = MessagesResponse.class, message =
                    "Ошибка при просмотре "),
    })
    TasksResponse lookAll(@Valid @RequestBody AllTaskRequest request);

    @PreAuthorize("hasRole('Оператор')")
    @Transactional
    @PutMapping("/update/{id}")
    @ApiResponses({
            @ApiResponse(code = 200, response = SuccessfulResponse.class, message =
                    "Успешный ответ: "),
            @ApiResponse(code = 400,  response = MessagesResponse.class, message =
                    "Ошибка при обновлени статуса"),
    })
    SuccessResponse update(@Valid @RequestBody ChangeStatusRequest request,
                           @Valid @PathVariable(name = "id") String id);
}

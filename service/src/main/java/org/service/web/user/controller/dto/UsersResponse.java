package org.service.web.user.controller.dto;

import lombok.*;
import org.service.web.user.entity.User;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Validated
public class UsersResponse {
    List<User> users;
}

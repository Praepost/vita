package org.service.web.user.controller.dto;

import lombok.*;
import org.springframework.validation.annotation.Validated;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Validated
public class OperatorRequst {
    String username;
}

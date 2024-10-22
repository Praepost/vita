package org.service.web.user.controller.dto;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Validated
public class RegistrationRequest {
    @Size(min = 5, message = "Не соответствует формату min:5")
    @NotBlank
    private String username;
    @NotBlank
    private String passwrod;
    @NotBlank
    private String role;
}
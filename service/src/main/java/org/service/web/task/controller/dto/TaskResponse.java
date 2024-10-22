package org.service.web.task.controller.dto;

import lombok.*;
import org.springframework.validation.annotation.Validated;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Validated
public class TaskResponse {
    @NonNull
    private Long id;
    private String name;
    @NonNull
    private String message;
    @NonNull
    private String author;
    @NonNull
    private Long timestamp;
}

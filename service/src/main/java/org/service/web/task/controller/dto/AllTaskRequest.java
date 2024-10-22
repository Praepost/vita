package org.service.web.task.controller.dto;

import lombok.*;
import org.springframework.validation.annotation.Validated;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Validated
public class AllTaskRequest {
    @NonNull
    Boolean desc;
    @NonNull
    Integer page;
}

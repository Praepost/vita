package org.service.web.task.controller.dto;

import lombok.*;
import org.service.web.task.entity.Task;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Validated
public class TasksResponse {
    @NonNull
    List<Task> taskList;
}

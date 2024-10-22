package org.service.web.task.controller;

import lombok.RequiredArgsConstructor;
import org.service.web.task.controller.dto.*;
import org.service.web.task.service.TaskService;
import org.service.web.user.controller.dto.SuccessResponse;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class TestTaskController implements ITaskController {

    private final TaskService service;

    @Override
    public SuccessResponse registration(CreateTaskRequest request) {
        return service.registration(request.getName(), request.getMessage());

    }

    @Override
    public SuccessResponse storage(StorageRequest request, String id) {
        return service.storage(request.getName(), id);
    }

    @Override
    public SuccessResponse edit(EditRequest request, String id) {
        return service.edit(request.getName(), request.getMessage(), id);
    }

    @Override
    public TaskResponse lookAthor(String id) {
        return service.lookAthor(id);
    }

    @Override
    public TasksResponse lookAuthor(TasksRequest request) {
        return service.lookAuthor(request.getPage(), request.getDesc(), request.getUser());
    }

    @Override
    public TasksResponse lookAll(AllTaskRequest request) {
        return service.lookAll(request.getDesc(), request.getPage());
    }

    @Override
    public SuccessResponse update(ChangeStatusRequest request, String id) {
        return service.update(request.getName(), request.getStatus(), id);
    }
}

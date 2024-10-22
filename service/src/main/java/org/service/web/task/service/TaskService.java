package org.service.web.task.service;

import lombok.RequiredArgsConstructor;
import org.service.web.task.controller.dto.TaskResponse;
import org.service.web.task.controller.dto.TasksResponse;
import org.service.web.task.entity.Statuses;
import org.service.web.task.entity.Task;
import org.service.web.task.entity.repository.StatusRepo;
import org.service.web.task.entity.repository.TaskRepo;
import org.service.web.task.exception.DraftException;
import org.service.web.task.exception.StatusException;
import org.service.web.task.exception.TaskNotFoundException;
import org.service.web.task.exception.UnavailableException;
import org.service.web.user.controller.dto.SuccessResponse;
import org.service.web.user.entity.User;
import org.service.web.user.entity.repository.UserRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TaskService {
    private final TaskRepo taskRepo;
    private final UserRepo userRepo;
    private final StatusRepo statusRepo;

    public SuccessResponse registration(String name, String messag) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.getUserByUsername(username);
        HashSet<User> users = new HashSet<>();
        users.add(user);

        Statuses statuse = new Statuses("черновик");
        statusRepo.save(statuse);

        List<Statuses> statuses = new ArrayList<>();
        statuses.add(statuse);

        Task task = new Task();
        task.setAuthor(users);
        task.setMessage(messag);
        task.setStatuses(statuses);
        task.setName(name);
        task.setTimestamp(System.currentTimeMillis());

        taskRepo.save(task);

        return new SuccessResponse("Черновик сохранен");
    }

    public SuccessResponse storage(String name, String id) {
        List<Statuses> statuses = new ArrayList<>();
        statuses.add(statusRepo.findByName("отправлено"));

        Optional<Task> tasks = taskRepo.findById(Long.valueOf(id));
        if (tasks.isEmpty()) throw new TaskNotFoundException("таска не найдена по айди");

        Task task = tasks.get();
        if (task.getStatuses().stream()
                .map(v -> v.getName())
                .collect(Collectors.toList()).contains(("черновик"))) {
            task.setStatuses(statuses);
        } else throw new StatusException("У заявки статус не отправленно");
        taskRepo.save(task);

        return new SuccessResponse("Успешно отправленно");
    }

    public SuccessResponse edit(String name, String message, String id) {

        Optional<Task> task = taskRepo.findById(Long.valueOf(id));
        if (task.isEmpty()) {
            throw new TaskNotFoundException("таска не найдена по id");
        }
        Task tasks = task.get();
        if (tasks.getStatuses().stream()
                .map(v -> v.getName())
                .collect(Collectors.toList()).contains(("черновик"))) {
            tasks.setMessage(message);
            tasks.setName(name);
        } else throw new DraftException("У заявки статус не черновик");

        return new SuccessResponse("Успешно обновленно");
    }

    public TaskResponse lookAthor(String id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.getUserByUsername(username);
        String result = "";

        Optional<Task> tasks = taskRepo.findById(Long.valueOf(id));
        if (tasks.isEmpty()) throw new TaskNotFoundException("таска по айди не найдена");
        Task task = tasks.get();
        task.getStatuses();
        String message = task.getMessage();

        if (user.getRoles().stream().map(v -> v.getName())
                .collect(Collectors.toList()).contains(("Пользователь"))) {
            result = message;
        } else if (user.getRoles().stream().map(v -> v.getName())
                .collect(Collectors.toList()).contains(("Оператор")))
            if (task.getStatuses().stream()
                    .map(v -> v.getName())
                    .collect(Collectors.toList()).contains(("отправлено"))) {
                char[] chars = message.toCharArray();
                for (char c : chars) {
                    result += String.valueOf(c) + "-";
                }
            }
        return new TaskResponse(task.getId(), task.getName(), result, task.getAuthor().stream().findFirst().get().getUsername(), task.getTimestamp());
    }

    public TasksResponse lookAuthor(Integer page, Boolean desc, String usernames) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.getUserByUsername(username);

        Pageable pages = null;
        List<Task> tasks = new ArrayList<>();

        if(user.getRoles().stream().map(v -> v.getName())
                .collect(Collectors.toList()).contains(("Оператор"))){
            user = userRepo.getUserByUsername(usernames);
            if(desc){
                pages = PageRequest.of(page, 5, Sort.by("timestamp").descending());
            } else {
                pages = PageRequest.of(page, 5, Sort.by("timestamp"));
            }
        } else if(user.getRoles().stream().map(v -> v.getName())
                .collect(Collectors.toList()).contains(("Пользователь"))){
            if(desc){
                pages = PageRequest.of(page, 5, Sort.by("timestamp").descending());
            } else {
                pages = PageRequest.of(page, 5, Sort.by("timestamp"));
            }
            tasks = taskRepo.findTasksByAuthor(user, pages);
        } else {
            throw new UnavailableException("Администратор не имеет прав");
        }

        return new TasksResponse(tasks);
    }

    public TasksResponse lookAll(Boolean desc,Integer page) {

        Pageable pages = null;
        if(desc){
            pages = PageRequest.of(page, 5, Sort.by("timestamp").descending());
        } else {
            pages = PageRequest.of(page, 5, Sort.by("timestamp"));
        }

        List<Task> tasks = taskRepo.findTasksByStatuses("«отправлено»", pages);


        return new TasksResponse(tasks);
    }

    public SuccessResponse update(String name, String status, String id) {
        Optional<Task> tasks = taskRepo.findById(Long.valueOf(id));

        Task task = tasks.get();

        List<Statuses> statuses = new ArrayList<>();
        Statuses statuses1 = new Statuses(status);
        statusRepo.save(statuses1);

        statuses.add(statuses1);

        task.setName(name);
        task.setStatuses(statuses);
        taskRepo.save(task);

        return new SuccessResponse("Успешно обновлен статус");
    }
}

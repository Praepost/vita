package org.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.service.web.task.controller.dto.*;
import org.service.web.task.entity.Task;
import org.service.web.task.entity.repository.StatusRepo;
import org.service.web.task.entity.repository.TaskRepo;
import org.service.web.user.entity.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
        @SpringBootTest
        @AutoConfigureMockMvc
        @ActiveProfiles({"test"})
        @Slf4j
        public class TestTaskController {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private StatusRepo statusRepo;
    @Test
    @WithMockUser(username = "Пользователь", roles = {"Пользователь"})
    public void createTaskRequestSuccess() {
        Task task = null;

        try {

            CreateTaskRequest createTaskRequest =
                    new CreateTaskRequest("name", "message");

            mockMvc.perform(post("/")
                    .content(objectMapper.writeValueAsString(createTaskRequest))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk());

            String username = SecurityContextHolder.getContext().getAuthentication().getName();

            task = taskRepo.findByName("name");

            assertEquals(task.getMessage(), createTaskRequest.getMessage());
            assertEquals(task.getName(), createTaskRequest.getName());
            assertEquals(task.getAuthor(), username);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            log.info("finally");
            taskRepo.deleteAll();
            statusRepo.deleteAll();
        }
    }

    @Test
    @WithMockUser(username = "Пользователь", roles = {"Пользователь"})
    public void storageTaskRequestSuccess() throws Exception {
        try {

            CreateTaskRequest createTaskRequest =
                    new CreateTaskRequest("name", "message");

            
            mockMvc.perform(post("/")
                    .content(objectMapper.writeValueAsString(createTaskRequest))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk());

            Task task = taskRepo.findByName("name");
            StorageRequest storageRequest =
                    new StorageRequest("name");

            mockMvc.perform(post("/posts/"+ task.getId())
                    .content(objectMapper.writeValueAsString(storageRequest))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk());

            assertEquals(storageRequest.getName(), task.getName());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            log.info("finally");
            taskRepo.deleteAll();
            statusRepo.deleteAll();

        }
    }

    @Test
    public void editTaskRequestSuccess() throws Exception {

        try {

            CreateTaskRequest createTaskRequest =
                    new CreateTaskRequest("name", "message");

            mockMvc.perform(post("/")
                    .content(objectMapper.writeValueAsString(createTaskRequest))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(user("Пользователь").password("Пользователь").roles("Пользователь"))
            ).andExpect(status().isOk());

            Task task = taskRepo.findByName(createTaskRequest.getName());


            EditRequest editRequest = new EditRequest("name2", "message2");

            mockMvc.perform(put("/edit/"+ task.getId())
                    .content(objectMapper.writeValueAsString(editRequest))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(user("Пользователь").password("Пользователь").roles("Пользователь"))
            ).andExpect(status().isOk());


            task = taskRepo.findByName(editRequest.getName());
            assertEquals(editRequest.getName(), task.getName());
            assertEquals(editRequest.getMessage(), task.getMessage());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            log.info("finally");
            taskRepo.deleteAll();
            statusRepo.deleteAll();

        }
    }

    @Test
    public void lookAthorTaskRequestOperatorSuccess() throws Exception {
        try {

            CreateTaskRequest createTaskRequest =
                    new CreateTaskRequest("name", "message");

            mockMvc.perform(post("/")
                    .content(objectMapper.writeValueAsString(createTaskRequest))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(user("Пользователь").password("Пользователь").roles("Пользователь"))
            ).andExpect(status().isOk());

            Task task = taskRepo.findByName(createTaskRequest.getName());

            StorageRequest storageRequest = new StorageRequest("name");

            mockMvc.perform(post("/posts/"+ task.getId())
                    .content(objectMapper.writeValueAsString(storageRequest))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(user("Пользователь").password("Пользователь").roles("Пользователь"))
            ).andExpect(status().isOk());

            String taskResponse = mockMvc.perform(post("/posts/" + task.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(user("Оператор").password("Оператор").roles("Оператор"))
            ).andReturn().getResponse().getContentAsString();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            log.info("finally");
            taskRepo.deleteAll();
            statusRepo.deleteAll();

        }
    }

    @Test
    public void lookAthorTaskRequestUserSuccess() throws Exception {
        try {

            CreateTaskRequest createTaskRequest =
                    new CreateTaskRequest("name", "message2");

            mockMvc.perform(post("/")
                    .content(objectMapper.writeValueAsString(createTaskRequest))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(user("Пользователь").password("Пользователь").roles("Пользователь"))
            ).andExpect(status().isOk());

            Task task = taskRepo.findByName(createTaskRequest.getName());

            StorageRequest storageRequest =
                    new StorageRequest("name");

            mockMvc.perform(post("/posts/"+ task.getId())
                    .content(objectMapper.writeValueAsString(storageRequest))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(user("Пользователь").password("Пользователь").roles("Пользователь"))
            ).andExpect(status().isOk());


            task = taskRepo.findByName(createTaskRequest.getName());
            TaskRequest taskRequest = new TaskRequest("name");

            String taskResponse = mockMvc.perform(post("/look/"+ task.getId())
                    .content(objectMapper.writeValueAsString(taskRequest))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(user("Пользователь").password("Пользователь").roles("Пользователь"))
            ).andReturn().getResponse().getContentAsString();

            assertEquals(true, taskResponse.contains("message2"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            log.info("finally");
            taskRepo.deleteAll();
            statusRepo.deleteAll();

        }
    }

    @Test
    public void lookAthorTaskAuthorRequestUserSuccess() throws Exception {

        try {

            CreateTaskRequest createTaskRequest =
                    new CreateTaskRequest("name", "message");

            mockMvc.perform(post("/")
                    .content(objectMapper.writeValueAsString(createTaskRequest))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(user("Пользователь").password("Пользователь").roles("Пользователь"))
            ).andExpect(status().isOk());


            StorageRequest storageRequest =
                    new StorageRequest("name");

            TasksRequest taskRequest = new TasksRequest(0, true, "Пользователь");

            String tasksResponse = mockMvc.perform(post("/look/author/")
                    .content(objectMapper.writeValueAsString(taskRequest))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(user("Пользователь").password("Пользователь").roles("Пользователь"))
            ).andReturn().getResponse().getContentAsString();

            assertEquals(true, tasksResponse.contains("name"));
            assertEquals(true, tasksResponse.contains("message"));


        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            log.info("finally");
            taskRepo.deleteAll();
            statusRepo.deleteAll();

        }
    }

    @Test
    public void lookAthorTaskAuthorRequestUserAllSuccess() throws Exception {

        try {

            CreateTaskRequest createTaskRequest =
                    new CreateTaskRequest("name", "message");

            mockMvc.perform(post("/")
                    .content(objectMapper.writeValueAsString(createTaskRequest))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(user("Пользователь").password("Пользователь").roles("Пользователь"))
            ).andExpect(status().isOk());

            Task task = taskRepo.findByName("name");

            StorageRequest storageRequest = new StorageRequest("name");
            mockMvc.perform(post("/posts/" + task.getId())
                    .content(objectMapper.writeValueAsString(storageRequest))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(user("Пользователь").password("Пользователь").roles("Пользователь"))
            ).andExpect(status().isOk());

            AllTaskRequest taskRequest = new AllTaskRequest(true, 0);

            String tasksResponse = mockMvc.perform(get("/tasks/")
                    .content(objectMapper.writeValueAsString(taskrequest))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(user("Оператор").password("Оператор").roles("Оператор"))
            ).andReturn().getResponse().getContentAsString();

            assertEquals(true, tasksResponse.contains("name"));
            assertEquals(true, tasksResponse.contains("message"));


        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            log.info("finally");
            taskRepo.deleteAll();
            statusRepo.deleteAll();

        }
    }

    @Test
    public void lookAthorTaskAuthorRequestUserUpdate() throws Exception {

        try {

            CreateTaskRequest createTaskRequest =
                    new CreateTaskRequest("name", "message");

            mockMvc.perform(post("/")
                    .content(objectMapper.writeValueAsString(createTaskRequest))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(user("Пользователь").password("Пользователь").roles("Пользователь"))
            ).andExpect(status().isOk());

//            StorageRequest storageRequest = new StorageRequest("name");

//            mockMvc.perform(post("/look/"+ task.getId())
//                    .with(user("Пользователь").password("Пользователь").roles("Пользователь"))
//            ).andExpect(status().isOk());

            Task task = taskRepo.findByName(createTaskRequest.getName());

            ChangeStatusRequest ChangeStatusRequest = new ChangeStatusRequest("name2", "status2");

            mockMvc.perform(put("/update/"+ task.getId())
                    .content(objectMapper.writeValueAsString(ChangeStatusRequest))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(user("Оператор").password("Оператор").roles("Оператор"))
            ).andReturn().getResponse().getContentAsString();

            Optional<Task> task2O = taskRepo.findById(task.getId());
            Task task2 = task2O.get();
            assertEquals("name2", task2.getName());
            assertEquals("status2", task2.getStatuses().get(0).getName());


        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            log.info("finally");
            taskRepo.deleteAll();
            statusRepo.deleteAll();

        }
    }
}
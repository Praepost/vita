package org.service.web.task.entity.repository;

import org.service.web.task.entity.Task;
import org.service.web.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends PagingAndSortingRepository<Task, Long> {
//    Boolean existsByUsername(String name);
//    User findUserByUsername(String username);
//    List<User> findUsersByUsernameContainingIgnoreCase(String username, Pageable pageable);
//    List<User> findUserByActive(Boolean active, Pageable pageable);
//    List<Task> findBy
//    List<Task> findTasksByAuthor(String author, Pageable pageable);
    List<Task> findTasksByAuthor(User user, Pageable pageable);
    List<Task> findTasksByStatuses(String statuses, Pageable pageable);
    Task findByName (String name);
}

package org.service.web.task.entity.repository;

import org.service.web.task.entity.Statuses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepo extends JpaRepository<Statuses, Long> {
    Statuses findByName (String name);
}

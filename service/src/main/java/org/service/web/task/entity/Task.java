package org.service.web.task.entity;

import lombok.*;
import org.service.web.user.entity.User;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "task")
@ToString
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NonNull
    private String name;
    @NonNull
    private String message;
    @NonNull
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name="userId",referencedColumnName="id")
    private Set<User> author;
    @NonNull
    private Long timestamp;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name="statusesId",referencedColumnName="id")
    private List<Statuses> statuses;
}

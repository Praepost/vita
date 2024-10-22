package org.service.web.task.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "statuses")
@ToString
public class Statuses {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NonNull
    private String name;
}

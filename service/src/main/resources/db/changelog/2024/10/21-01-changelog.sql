-- liquibase formatted sql

-- changeset user:1729522138043-1
CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

-- changeset user:1729522138043-2
CREATE TABLE role
(
    id   BIGINT NOT NULL,
    name VARCHAR(20),
    CONSTRAINT pk_role PRIMARY KEY (id)
);

-- changeset user:1729522138043-3
CREATE TABLE statuses
(
    id   BIGINT NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_statuses PRIMARY KEY (id)
);

-- changeset user:1729522138043-4
CREATE TABLE task
(
    id        BIGINT NOT NULL,
    name      VARCHAR(255),
    message   VARCHAR(255),
    timestamp BIGINT,
    CONSTRAINT pk_task PRIMARY KEY (id)
);

-- changeset user:1729522138043-5
CREATE TABLE task_author
(
    task_id   BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    CONSTRAINT pk_task_author PRIMARY KEY (task_id, author_id)
);

-- changeset user:1729522138043-6
CREATE TABLE task_statuses
(
    task_id     BIGINT NOT NULL,
    statuses_id BIGINT NOT NULL
);

-- changeset user:1729522138043-7
CREATE TABLE "user"
(
    id       BIGINT  NOT NULL,
    username VARCHAR(255),
    password VARCHAR(255),
    active   BOOLEAN NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

-- changeset user:1729522138043-8
CREATE TABLE user_roles
(
    user_id  BIGINT NOT NULL,
    roles_id BIGINT NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (user_id, roles_id)
);

-- changeset user:1729522138043-9
ALTER TABLE task_author
    ADD CONSTRAINT fk_tasaut_on_task FOREIGN KEY (task_id) REFERENCES task (id);

-- changeset user:1729522138043-10
ALTER TABLE task_author
    ADD CONSTRAINT fk_tasaut_on_user FOREIGN KEY (author_id) REFERENCES "user" (id);

-- changeset user:1729522138043-11
ALTER TABLE task_statuses
    ADD CONSTRAINT fk_tassta_on_statuses FOREIGN KEY (statuses_id) REFERENCES statuses (id);

-- changeset user:1729522138043-12
ALTER TABLE task_statuses
    ADD CONSTRAINT fk_tassta_on_task FOREIGN KEY (task_id) REFERENCES task (id);

-- changeset user:1729522138043-13
ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (roles_id) REFERENCES role (id);

-- changeset user:1729522138043-14
ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES "user" (id);


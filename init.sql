CREATE DATABASE IF NOT EXISTS t_jobs;
USE t_jobs;

CREATE TABLE staff (
    id          SERIAL,
    name        VARCHAR(100) NOT NULL,
    surname     VARCHAR(100),
    photo_url   TEXT,

    PRIMARY KEY (id)
);

INSERT INTO staff (name, surname, photo_url) VALUES
    ('Мария', 'Грек', 'https://tatmitropolia.ru/www/images/ikony_svyatych/maksim_grek_595.jpg'),      
    ('Эрих', 'Ремарк', 'https://brsbs.ru/sites/default/files/news/images/erih-mariya-remark.jpg'),    
    ('Байрон', 'Депампадур', 'https://upload.wikimedia.org/wikipedia/commons/thumb/c/c4/Boucher_Pompadour_Munich_04.jpg/800px-Boucher_Pompadour_Munich_04.jpg');     

CREATE TABLE credentials (
    staff_id    BIGINT UNSIGNED NOT NULL UNIQUE,
    login       VARCHAR(50)     NOT NULL UNIQUE,
    password    TEXT            NOT NULL,
    is_hr       BOOLEAN,
    is_tl       BOOLEAN,
    is_interv   BOOLEAN,

    PRIMARY KEY (staff_id),

    FOREIGN KEY (staff_id) REFERENCES staff(id)
);

INSERT INTO credentials (staff_id, login, password, is_hr, is_tl, is_interv) VALUES
    (1, 'hr1',     '$2a$10$CkXp/VOHNfd7Efk0KJDV1uRzBiZa8c4aBL5EIfvTkvrLBTWOyOSce', 1, 0, 0),     -- pass: 1234
    (2, 'tl1',     '$2a$10$i5MSqQkWiOmu.Zd5OJ7U1uLG0n/MflVmFqcwyognunCjI2BaIiuPi', 0, 1, 0),     -- pass: 4321
    (3, 'interv1', '$2a$10$5jOI0NE3rgcMSRT1kjouFuYIx9.cQXMfHq6oQ2yca5BWIk3tjEG8G', 0, 0, 1);     -- pass: 1243

CREATE TABLE candidate (
    id      SERIAL,
    name    VARCHAR(50) NOT NULL,
    surname VARCHAR(50),
    tg_id   VARCHAR(50),
    town    VARCHAR(50), 

    PRIMARY KEY (id)
);

INSERT INTO candidate (name, surname, tg_id, town) VALUES
    ('Алексей', 'Трясков', '@4rter', 'Sp5'),    
    ('Илья', 'Секунов', '@ilyaSekunov', 'Канзас'),     
    ('Александр', 'Кулюкин', '@alexkul', 'Ченазес');     

CREATE TABLE vacancy (
    id          SERIAL,
    name        VARCHAR(100) NOT NULL,
    description TEXT,
    salary      INT,
    town        VARCHAR(50),

    PRIMARY KEY(id)
);

INSERT INTO vacancy (name, description, salary, town) VALUES
    ('Java-разработчик', 'Какое-то описание типичного пещерного джависта', 50, 'Javaland'),    
    ('Kotlin-рофлер', 'Я свинка пепа. *** ** ***', 666666, 'Kotlyandiya');   

CREATE TABLE track (
    id              SERIAL,
    hr_id           BIGINT UNSIGNED NOT NULL,
    candidate_id    BIGINT UNSIGNED NOT NULL,
    vacancy_id      BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY(id),

    FOREIGN KEY (hr_id)         REFERENCES staff(id),
    FOREIGN KEY (candidate_id)  REFERENCES candidate(id),
    FOREIGN KEY (vacancy_id)    REFERENCES vacancy(id)
);

INSERT INTO track (hr_id, candidate_id, vacancy_id) VALUES
    (1, 1, 1),    
    (1, 2, 2);   

CREATE TABLE interview_type (
    id SERIAL,
    name VARCHAR(100) NOT NULL UNIQUE,

    PRIMARY KEY(id)
);

INSERT INTO interview_type (name) VALUES
    ('Практика'), ('Теория'), ('Скрининг');

CREATE TABLE interview (
    id                  SERIAL,
    interview_order     INT             NOT NULL,
    interview_type_id   BIGINT UNSIGNED NOT NULL,
    track_id            BIGINT UNSIGNED NOT NULL,
    interviewer_id      BIGINT UNSIGNED,
    date_picked         TIMESTAMP,
    date_approved       BOOLEAN DEFAULT FALSE,
    feedback            TEXT,
    success             BOOLEAN DEFAULT FALSE,

    PRIMARY KEY (id),

    FOREIGN KEY (interview_type_id) REFERENCES interview_type(id),
    FOREIGN KEY (track_id)          REFERENCES track(id),
    FOREIGN KEY (interviewer_id)    REFERENCES staff(id)
);

INSERT INTO interview (interview_type_id, track_id, interviewer_id, date_picked, date_approved, feedback, success, interview_order) VALUES
    (3, 1, 1, '2024-10-01 12:23:00', TRUE, 'Нормально', TRUE, 1),    
    (2, 1, 3, '2025-10-02 17:45:00', FALSE, NULL, FALSE, 2),
    (1, 1, NULL, NULL, FALSE, NULL, FALSE, 3),
    (3, 2, 1, '2024-09-12 10:30:00', TRUE, 'Overqualified', FALSE, 1);

CREATE TABLE interview_type_stuff (
    interview_type_id   BIGINT UNSIGNED NOT NULL,
    staff_id            BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (interview_type_id, staff_id),

    FOREIGN KEY (interview_type_id) REFERENCES interview_type(id),
    FOREIGN KEY (staff_id)          REFERENCES staff(id)
);

INSERT INTO interview_type_stuff (interview_type_id, staff_id) VALUES
    (1, 3), (2, 3), (3, 1);

CREATE TABLE interview_base (
    id SERIAL,
    interview_type_id   BIGINT UNSIGNED NOT NULL,
    vacancy_id          BIGINT UNSIGNED NOT NULL,
    interview_order INT,

    PRIMARY KEY (id),

    FOREIGN KEY (interview_type_id) REFERENCES interview_type(id),
    FOREIGN KEY (vacancy_id)        REFERENCES vacancy(id)
);

INSERT INTO interview_base (interview_type_id, vacancy_id, interview_order) VALUES
    (3, 1, 1), (3, 2, 1), (1, 1, 2), (2, 1, 3);

CREATE TABLE staff_vacancy (
    staff_id    BIGINT UNSIGNED NOT NULL,
    vacancy_id  BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (staff_id, vacancy_id),

    FOREIGN KEY (staff_id)      REFERENCES staff(id),
    FOREIGN KEY (vacancy_id)    REFERENCES vacancy(id)
);

INSERT INTO staff_vacancy (staff_id, vacancy_id) VALUES
    (1, 1), (1, 2), (2, 1), (2, 2);

CREATE TABLE tag (
    id          SERIAL,
    category    ENUM('type1', 'type2'),
    name        VARCHAR(50),

    PRIMARY KEY (id)
);

INSERT INTO tag (category, name) VALUES
    ('type1', 'Java'), ('type1', 'Sql'), ('type1', 'Kotlin');

CREATE TABLE vacancy_tag (
    vacancy_id  BIGINT UNSIGNED NOT NULL,
    tag_id      BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (vacancy_id, tag_id),

    FOREIGN KEY (vacancy_id)    REFERENCES vacancy(id),
    FOREIGN KEY (tag_id)        REFERENCES tag(id)
);

INSERT INTO vacancy_tag (vacancy_id, tag_id) VALUES
    (1, 1), (1, 2), (2, 3);

CREATE TABLE resume (
    id              SERIAL,
    candidate_id    BIGINT UNSIGNED NOT NULL,
    salary_min      INT,
    `description`     TEXT,

    PRIMARY KEY(id),

    FOREIGN KEY (candidate_id) REFERENCES candidate(id)
);

INSERT INTO resume (candidate_id, salary_min, description) VALUES
    (1, 10, 'Какое-то описание.'), (2, 600000, 'Еще одно описание.'), (3, 59, 'Да, тут тоже описание.');

CREATE TABLE resume_tag (
    resume_id   BIGINT UNSIGNED NOT NULL,
    tag_id      BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (resume_id, tag_id),

    FOREIGN KEY (resume_id) REFERENCES resume(id),
    FOREIGN KEY (tag_id)    REFERENCES tag(id)
);

INSERT INTO resume_tag (resume_id, tag_id) VALUES
    (1, 2), (2, 3), (3, 1), (3, 2);


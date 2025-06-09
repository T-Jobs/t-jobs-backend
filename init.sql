CREATE DATABASE IF NOT EXISTS t_jobs;
USE t_jobs;

CREATE TABLE role (
    id          SERIAL,
    name        VARCHAR(50) NOT NULL,

    PRIMARY KEY (id)
);

INSERT INTO role (name) VALUES
    ('HR'), ('TL'), ('INTERVIEWER');

CREATE TABLE staff (
    id                  SERIAL,
    name                VARCHAR(100) NOT NULL,
    surname             VARCHAR(100),
    photo_url           TEXT,
    interviewer_mode    BOOLEAN DEFAULT FALSE,

    PRIMARY KEY (id)
);

INSERT INTO staff (name, surname, photo_url, interviewer_mode) VALUES
    ('Мария', 'Грек', 'https://tatmitropolia.ru/www/images/ikony_svyatych/maksim_grek_595.jpg', TRUE),
    ('Эрих', 'Ремарк', 'https://brsbs.ru/sites/default/files/news/images/erih-mariya-remark.jpg', FALSE),
    ('Байрон', 'Депампадур', 'https://upload.wikimedia.org/wikipedia/commons/thumb/c/c4/Boucher_Pompadour_Munich_04.jpg/800px-Boucher_Pompadour_Munich_04.jpg', TRUE);

CREATE TABLE staff_role (
    staff_id    BIGINT UNSIGNED NOT NULL,
    role_id     BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (staff_id, role_id),

    FOREIGN KEY (staff_id) REFERENCES staff(id),
    FOREIGN KEY (role_id) REFERENCES role(id)
);

INSERT INTO staff_role (staff_id, role_id) VALUES
    (1, 1), (2, 2), (3, 3);

CREATE TABLE credentials (
    staff_id    BIGINT UNSIGNED NOT NULL UNIQUE,
    login       VARCHAR(50)     NOT NULL UNIQUE,
    password    TEXT            NOT NULL,

    PRIMARY KEY (staff_id),

    FOREIGN KEY (staff_id) REFERENCES staff(id)
);

INSERT INTO credentials (staff_id, login, password) VALUES
    (1, 'hr1',     '$2a$10$CkXp/VOHNfd7Efk0KJDV1uRzBiZa8c4aBL5EIfvTkvrLBTWOyOSce'),     -- pass: 1234
    (2, 'tl1',     '$2a$10$i5MSqQkWiOmu.Zd5OJ7U1uLG0n/MflVmFqcwyognunCjI2BaIiuPi'),     -- pass: 4321
    (3, 'interv1', '$2a$10$5jOI0NE3rgcMSRT1kjouFuYIx9.cQXMfHq6oQ2yca5BWIk3tjEG8G');     -- pass: 1243

CREATE TABLE candidate (
    id          SERIAL,
    name        VARCHAR(50) NOT NULL,
    surname     VARCHAR(50),
    photo_url   TEXT,
    tg_id       VARCHAR(50),
    town        VARCHAR(50),
    chat_id     BIGINT UNIQUE,

    PRIMARY KEY (id)
);

INSERT INTO candidate (name, surname, tg_id, town, chat_id) VALUES
    ('Алексей', 'Трясков', 'ale4rter', 'Sp5', 228),
    ('Илья', 'Секунов', 'ilyaSekunov', 'Канзас', 1293444611),
    ('Александр', 'Кулюкин', 'kula1ex', 'Ченазес', 1063386066);

CREATE TABLE vacancy (
    id          SERIAL,
    name        VARCHAR(100) NOT NULL,
    description TEXT,
    salary_min  INT,
    salary_max  INT,
    town        VARCHAR(50),

    PRIMARY KEY(id)
);

INSERT INTO vacancy (name, description, salary_max, town) VALUES
    ('Java-разработчик', 'Какое-то описание типичного пещерного джависта', 50, 'Javaland'),    
    ('Kotlin-рофлер',
        'Ты будешь:
        ✔ Писать код, от которого плачут ангелы – максимально компактный, без комментариев, с магическими числами и !! на каждом шагу.
        ✔ Извращаться с inline-функциями, чтобы никто, включая тебя через месяц, не понял, как это работает.
        ✔ Заменять when на цепочку if-else, чтобы код выглядел как лабиринт Минотавра.
        ✔ Игнорировать SOLID, потому что хаос – наше второе имя.
        ✔ Оптимизировать не скорость работы, а количество строк кода – даже если это превратит O(1) в O(nⁿ).',
     666666, 'Kotlyandiya');

CREATE TABLE track (
    id              SERIAL,
    hr_id           BIGINT UNSIGNED NOT NULL,
    candidate_id    BIGINT UNSIGNED NOT NULL,
    vacancy_id      BIGINT UNSIGNED NOT NULL,
    last_status     ENUM('FAILED', 'SUCCESS', 'WAITING_FEEDBACK', 'TIME_APPROVAL', 'NONE'),
    finished        BOOLEAN,

    PRIMARY KEY(id),

    FOREIGN KEY (hr_id)         REFERENCES staff(id),
    FOREIGN KEY (candidate_id)  REFERENCES candidate(id),
    FOREIGN KEY (vacancy_id)    REFERENCES vacancy(id)
);

INSERT INTO track (hr_id, candidate_id, vacancy_id, last_status, finished) VALUES
    (1, 2, 2, 'TIME_APPROVAL', FALSE),
    (1, 1, 1, 'FAILED', TRUE);

CREATE TABLE interview_type (
    id SERIAL,
    name VARCHAR(100)  NOT NULL UNIQUE,

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
    feedback            TEXT ,
    status              ENUM('FAILED', 'SUCCESS', 'WAITING_FEEDBACK', 'TIME_APPROVAL', 'NONE'),
    link                TEXT,

    PRIMARY KEY (id),

    FOREIGN KEY (interview_type_id) REFERENCES interview_type(id),
    FOREIGN KEY (track_id)          REFERENCES track(id),
    FOREIGN KEY (interviewer_id)    REFERENCES staff(id)
);

INSERT INTO interview (interview_type_id, track_id, interviewer_id, date_picked, date_approved, feedback, interview_order, status) VALUES
    (3, 1, 1,       '2024-10-01 12:23:00',  TRUE, 'Нормально',      0, 'SUCCESS'),
    (2, 1, 3,       '2025-10-02 17:45:00',  FALSE, NULL,            1, 'TIME_APPROVAL'),
    (1, 1, NULL,    NULL,                   FALSE, NULL,            2, 'NONE'),
    (3, 2, 1,       '2024-09-12 10:30:00',  TRUE, 'Overqualified',  0, 'FAILED');

CREATE TABLE interview_type_staff (
    interview_type_id   BIGINT UNSIGNED NOT NULL,
    staff_id            BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (interview_type_id, staff_id),

    FOREIGN KEY (interview_type_id) REFERENCES interview_type(id),
    FOREIGN KEY (staff_id)          REFERENCES staff(id)
);

INSERT INTO interview_type_staff (interview_type_id, staff_id) VALUES
    (1, 3), (2, 3), (3, 1);

CREATE TABLE base_interview (
    id SERIAL,
    interview_type_id   BIGINT UNSIGNED NOT NULL,
    vacancy_id          BIGINT UNSIGNED NOT NULL,
    interview_order INT,

    PRIMARY KEY (id),

    FOREIGN KEY (interview_type_id) REFERENCES interview_type(id),
    FOREIGN KEY (vacancy_id)        REFERENCES vacancy(id)
);

INSERT INTO base_interview (interview_type_id, vacancy_id, interview_order) VALUES
    (3, 1, 0), (3, 2, 0), (1, 1, 1), (2, 1, 2);

CREATE TABLE staff_vacancy (
    staff_id    BIGINT UNSIGNED NOT NULL,
    vacancy_id  BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (staff_id, vacancy_id),

    FOREIGN KEY (staff_id)      REFERENCES staff(id),
    FOREIGN KEY (vacancy_id)    REFERENCES vacancy(id)
);

INSERT INTO staff_vacancy (staff_id, vacancy_id) VALUES
    (1, 1), (1, 2), (2, 1), (2, 2);

CREATE TABLE tag_category (
    id          SERIAL,
    name        VARCHAR(50),

    PRIMARY KEY (id)
);

INSERT INTO tag_category (name) VALUES
    ('type1'), ('type2');

CREATE TABLE tag (
    id          SERIAL,
    category_id    BIGINT UNSIGNED NOT NULL,
    name        VARCHAR(50) ,

    PRIMARY KEY (id),

    FOREIGN KEY (category_id)   REFERENCES tag_category(id)
);

INSERT INTO tag (category_id, name) VALUES
    (1, 'Java'), (2, 'Sql'), (1, 'Kotlin');

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
    name            VARCHAR(50) NOT NULL,
    salary_min      INT,
    description     TEXT,
    date            DATE NOT NULL,

    PRIMARY KEY(id),

    FOREIGN KEY (candidate_id) REFERENCES candidate(id)
);

INSERT INTO resume (candidate_id, salary_min, description, date, name) VALUES
    (1, 10,     'Какое-то описание.',       "2015-03-12", "Dungeon master"),
    (2, 600000, 'Еще одно описание.',       "2018-09-23", "Kotlinофил"),
    (3, 59,     'Да, тут тоже описание.',   "2024-12-24", "Пока не определился");

CREATE TABLE resume_tag (
    resume_id   BIGINT UNSIGNED NOT NULL,
    tag_id      BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (resume_id, tag_id),

    FOREIGN KEY (resume_id) REFERENCES resume(id),
    FOREIGN KEY (tag_id)    REFERENCES tag(id)
);

INSERT INTO resume_tag (resume_id, tag_id) VALUES
    (1, 2), (2, 3), (3, 1), (3, 2);

CREATE TABLE candidate_applications (
    candidate_id    BIGINT UNSIGNED NOT NULL,
    vacancy_id      BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (candidate_id, vacancy_id),
    FOREIGN KEY (candidate_id)  REFERENCES candidate(id),
    FOREIGN KEY (vacancy_id)    REFERENCES vacancy(id)
);

INSERT INTO candidate_applications (candidate_id, vacancy_id) VALUES
    (1, 1), (2, 1), (3, 1), (3, 2);

CREATE TABLE new_candidate (
    id      SERIAL,
    chat_id BIGINT UNIQUE,

    PRIMARY KEY (id)
);

INSERT INTO new_candidate (chat_id) VALUES
    (1), (2);
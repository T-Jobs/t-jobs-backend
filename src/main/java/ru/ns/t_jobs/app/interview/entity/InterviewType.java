package ru.ns.t_jobs.app.interview.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "interview_type")
@Getter
public class InterviewType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100, unique = true)
    private String name;
}


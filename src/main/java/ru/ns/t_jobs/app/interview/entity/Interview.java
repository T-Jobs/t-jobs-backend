package ru.ns.t_jobs.app.interview.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "interview")
public class Interview {
    @Id
    private Long id;

    @Column(name = "interview_order", nullable = false)
    private Integer interviewOrder;

    @Column(name = "interview_type_id", nullable = false)
    private Long interviewTypeId;

    @Column(name = "track_id", nullable = false)
    private Long trackId;

    @Column(name = "interviewer_id")
    private Long interviewerId;

    @Column(name = "date_picked")
    private LocalDateTime datePicked;

    @Column(name = "date_approved", columnDefinition = "boolean default false")
    private Boolean dateApproved = false;

    @Column(name = "feedback", columnDefinition = "text")
    private String feedback;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private InterviewStatus status;
}



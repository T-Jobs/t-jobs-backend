package ru.ns.t_jobs.app.interview.entity;

import jakarta.persistence.*;
import lombok.Getter;
import ru.ns.t_jobs.app.TagCategory;
import ru.ns.t_jobs.app.staff.entity.Staff;
import ru.ns.t_jobs.app.track.entity.Track;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "interview")
public class Interview {
    @Id
    private long id;

    @Column(name = "interview_order", nullable = false)
    private Integer interviewOrder;

    @Column(name = "interview_type_id", nullable = false)
    private long interviewTypeId;

    @Column(name = "track_id", nullable = false)
    private long trackId;

    @ManyToOne
    @JoinColumn(name = "interviewer_id", referencedColumnName = "id", nullable = false)
    private Staff interviewer;

    @Column(name = "date_picked")
    private LocalDateTime datePicked;

    @Column(name = "date_approved", columnDefinition = "boolean default false")
    private boolean dateApproved = false;

    @Column(name = "feedback", columnDefinition = "text")
    private String feedback;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private InterviewStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "track_id", referencedColumnName = "id",
            insertable = false, updatable = false, nullable = false)
    private Track track;
}



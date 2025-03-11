package ru.ns.t_jobs.app.interview.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.ns.t_jobs.app.staff.entity.Staff;
import ru.ns.t_jobs.app.track.entity.Track;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "interview")
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "interview_order", nullable = false)
    private int interviewOrder;

    @ManyToOne
    @JoinColumn(name = "interview_type_id", referencedColumnName = "id", nullable = false)
    private InterviewType interviewType;

    @ManyToOne
    @JoinColumn(name = "interviewer_id", referencedColumnName = "id")
    private Staff interviewer;

    @Column(name = "date_picked")
    private LocalDateTime datePicked;

    @Column(name = "date_approved", columnDefinition = "boolean default false")
    private boolean dateApproved = false;

    @Column(name = "feedback", columnDefinition = "text")
    private String feedback;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private InterviewStatus status = InterviewStatus.NONE;

    @Column(name = "able_set_time")
    private boolean ableSetTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "track_id", referencedColumnName = "id")
    private Track track;

    @Column(name = "link", columnDefinition = "TEXT")
    private String link;
}



package ru.ns.t_jobs.app.interview.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ns.t_jobs.app.staff.entity.Staff;
import ru.ns.t_jobs.app.track.entity.Track;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@Setter
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
    private boolean dateApproved;

    @Column(name = "feedback", columnDefinition = "text")
    private String feedback;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private InterviewStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "track_id", referencedColumnName = "id")
    private Track track;

    @Column(name = "link", columnDefinition = "TEXT")
    private String link;

    public boolean isAbleSetTime() {
        if (status == InterviewStatus.SUCCESS || status == InterviewStatus.FAILED) return false;
        return interviewOrder == 0 || track.getInterviews()
                .get(interviewOrder - 1).getStatus() == InterviewStatus.SUCCESS;
    }
}



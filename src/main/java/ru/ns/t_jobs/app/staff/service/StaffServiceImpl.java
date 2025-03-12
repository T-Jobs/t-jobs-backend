package ru.ns.t_jobs.app.staff.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ns.t_jobs.app.interview.dto.InterviewConvertor;
import ru.ns.t_jobs.app.interview.dto.InterviewDto;
import ru.ns.t_jobs.app.interview.entity.Interview;
import ru.ns.t_jobs.app.interview.entity.InterviewTypeRepository;
import ru.ns.t_jobs.app.staff.dto.StaffConvertor;
import ru.ns.t_jobs.app.staff.dto.StaffInfoDto;
import ru.ns.t_jobs.app.staff.entity.Staff;
import ru.ns.t_jobs.app.staff.entity.StaffRepository;
import ru.ns.t_jobs.app.track.dto.TrackConvertor;
import ru.ns.t_jobs.app.track.dto.TrackInfoDto;
import ru.ns.t_jobs.app.vacancy.dto.VacancyConvertor;
import ru.ns.t_jobs.app.vacancy.dto.VacancyDto;
import ru.ns.t_jobs.app.vacancy.entity.VacancyRepository;
import ru.ns.t_jobs.auth.credentials.Role;
import ru.ns.t_jobs.auth.util.ContextUtils;
import ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static ru.ns.t_jobs.app.interview.entity.InterviewStatus.FAILED;
import static ru.ns.t_jobs.app.interview.entity.InterviewStatus.SUCCESS;
import static ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory.noSuchInterviewTypeException;
import static ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory.noSuchStaffException;
import static ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory.noSuchVacancyException;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffs;
    private final InterviewTypeRepository interviews;
    private final VacancyRepository vacancies;

    @Override
    public StaffInfoDto getUserInfo() {
        Staff s = staffs.getReferenceById(ContextUtils.getCurrentUserStaffId());
        return StaffConvertor.staffInfoDto(s);
    }

    @Override
    public Collection<String> getUserRoles() {
        Staff s = staffs.getReferenceById(ContextUtils.getCurrentUserStaffId());
        return s.getRoles().stream().map(Role::getName).toList();
    }

    @Override
    public List<VacancyDto> getUserVacancies() {
        return VacancyConvertor.vacancyDtos(
                staffs.getReferenceById(ContextUtils.getCurrentUserStaffId())
                        .getVacancies()
        );
    }

    @Override
    public List<InterviewDto> getUserInterviews(boolean onlyRelevant) {
        long staffId = ContextUtils.getCurrentUserStaffId();
        var filterInterviewTypes = Set.of(SUCCESS, FAILED);

        Stream<Interview> result = staffs.getReferenceById(staffId).getInterviews().stream();
        if (onlyRelevant) {
            result = result.filter(i -> !filterInterviewTypes.contains(i.getStatus()));
        }

        result = result.sorted((o1, o2) -> {
            if (Objects.equals(o1.getDatePicked(), o2.getDatePicked())) return 0;
            if (o1.getDatePicked() == null) return 1;
            if (o2.getDatePicked() == null) return -1;
            return o1.getDatePicked().isBefore(o2.getDatePicked()) ? -1 : 1;
        });
        return result.map(InterviewConvertor::interviewDto).toList();
    }

    @Override
    public List<TrackInfoDto> getHrTracks(boolean onlyRelevant) {
        return staffs.getReferenceById(ContextUtils.getCurrentUserStaffId()).getTracks()
                .stream().filter(t -> !onlyRelevant || t.isFinished()).map(TrackConvertor::trackInfoDto).toList();
    }

    @Override
    public List<StaffInfoDto> searchStaffByText(String text) {
        return StaffConvertor.staffInfoDtos(staffs.findByText(text));
    }

    @Override
    public List<StaffInfoDto> getStaffByIds(List<Long> ids) {
        var res = staffs.findAllById(ids);
        NotFoundExceptionFactory.containsAllOrThrow(
                res.stream().map(Staff::getId).toList(), ids,
                NotFoundExceptionFactory::noSuchStaffsException
        );
        return StaffConvertor.staffInfoDtos(res);
    }

    @Override
    public StaffInfoDto getStaffById(Long id) {
        return StaffConvertor.staffInfoDto(
                staffs.findById(id).orElseThrow(() -> noSuchStaffException(id))
        );
    }

    @Override
    public void setInterviewerMode(boolean interviewerMode) {
        Staff s = staffs.getReferenceById(ContextUtils.getCurrentUserStaffId());
        s.setInterviewerMode(interviewerMode);
        staffs.save(s);
    }

    @Override
    public void addInterviewTypeToInterviewer(long interviewTypeId) {
        var interviewType = interviews.findById(interviewTypeId);

        Staff s = staffs.getReferenceById(ContextUtils.getCurrentUserStaffId());
        s.getInterviewTypes().add(
                interviewType.orElseThrow(() -> noSuchInterviewTypeException(interviewTypeId))
        );
        staffs.save(s);
    }

    @Override
    public void removeInterviewTypeFromInterviewer(long interviewTypeId) {
        var interviewType = interviews.findById(interviewTypeId);

        Staff s = staffs.getReferenceById(ContextUtils.getCurrentUserStaffId());
        s.getInterviewTypes().remove(
                interviewType.orElseThrow(() -> noSuchInterviewTypeException(interviewTypeId))
        );
        staffs.save(s);
    }

    @Override
    public void followVacancy(long id) {
        Staff s = staffs.getReferenceById(ContextUtils.getCurrentUserStaffId());
        s.getVacancies().add(
                vacancies.findById(id)
                        .orElseThrow(() -> noSuchVacancyException(id))
        );
        staffs.save(s);
    }

    @Override
    public void unfollowVacancy(long id) {
        Staff s = staffs.getReferenceById(ContextUtils.getCurrentUserStaffId());
        s.getVacancies().remove(
                vacancies.findById(id)
                        .orElseThrow(() -> noSuchVacancyException(id))
        );
        staffs.save(s);
    }
}

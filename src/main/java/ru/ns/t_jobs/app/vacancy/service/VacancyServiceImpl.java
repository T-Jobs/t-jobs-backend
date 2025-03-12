package ru.ns.t_jobs.app.vacancy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.ns.t_jobs.app.interview.entity.BaseInterview;
import ru.ns.t_jobs.app.interview.entity.BaseInterviewRepository;
import ru.ns.t_jobs.app.interview.entity.InterviewTypeRepository;
import ru.ns.t_jobs.app.staff.entity.Staff;
import ru.ns.t_jobs.app.staff.entity.StaffRepository;
import ru.ns.t_jobs.app.tag.entity.Tag;
import ru.ns.t_jobs.app.tag.entity.TagRepository;
import ru.ns.t_jobs.app.vacancy.dto.EditOrCreateVacancyDto;
import ru.ns.t_jobs.app.vacancy.dto.VacancyConvertor;
import ru.ns.t_jobs.app.vacancy.dto.VacancyDto;
import ru.ns.t_jobs.app.vacancy.entity.Vacancy;
import ru.ns.t_jobs.app.vacancy.entity.VacancyRepository;
import ru.ns.t_jobs.auth.util.ContextUtils;
import ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory;

import java.util.ArrayList;
import java.util.List;

import static ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory.noSuchInterviewTypeException;
import static ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory.noSuchVacancyException;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {

    private final VacancyRepository vacancyRepository;
    private final StaffRepository staffRepository;
    private final BaseInterviewRepository baseInterviewRepository;
    private final TagRepository tagRepository;
    private final InterviewTypeRepository interviewTypeRepository;

    @Override
    public List<VacancyDto> searchVacancies(String text, int page, int pageSize, int salaryLowerBound, List<Long> tagIds) {
        Pageable paging = PageRequest.of(page, pageSize);
        List<Vacancy> vacancies;
        if (tagIds == null) {
            vacancies = vacancyRepository.findAllByTags(text, salaryLowerBound, paging);
        } else {
            vacancies = vacancyRepository.findAllByTags(text, tagIds, tagIds.size(), salaryLowerBound, paging);
        }

        return VacancyConvertor.vacancyDtos(vacancies);
    }

    @Override
    public VacancyDto createVacancy(EditOrCreateVacancyDto vacancyDto) {
        var v = vacancyRepository.save(createFrom(vacancyDto));
        List<BaseInterview> baseInterviews = from(vacancyDto.interviews(), v);
        v.setBaseInterviews(baseInterviewRepository.saveAll(baseInterviews));
        return VacancyConvertor.vacancyDto(v);
    }

    private Vacancy createFrom(EditOrCreateVacancyDto v) {
        Staff hr = staffRepository.getReferenceById(ContextUtils.getCurrentUserStaffId());

        return Vacancy.builder()
                .name(v.name())
                .staff(List.of(hr))
                .description(v.description())
                .salaryMin(v.salaryMin())
                .salaryMax(v.salaryMax())
                .town(v.town())
                .tags(tagsFrom(v.tags()))
                .build();
    }

    private List<Tag> tagsFrom(List<Long> ids) {
        var res = tagRepository.findAllById(ids);
        NotFoundExceptionFactory.containsAllOrThrow(
                res.stream().map(Tag::getId).toList(), ids,
                NotFoundExceptionFactory::noSuchTagsException);
        return res;
    }

    private List<BaseInterview> from(List<Long> tid, Vacancy v) {
        List<BaseInterview> baseInterviews = new ArrayList<>();
        for (int i = 0; i < tid.size(); i++) {
            final long baseId = tid.get(i);
            baseInterviews.add(BaseInterview.builder()
                    .interviewOrder(i)
                    .vacancy(v)
                    .interviewType(interviewTypeRepository.findById(baseId)
                            .orElseThrow(() -> noSuchInterviewTypeException(baseId)))
                    .build());
        }
        return baseInterviews;
    }

    @Override
    public VacancyDto editVacancy(EditOrCreateVacancyDto vacancyDto, long id) {
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> noSuchVacancyException(id));

        vacancy.setName(vacancyDto.name());
        vacancy.setDescription(vacancyDto.description());
        vacancy.setSalaryMin(vacancyDto.salaryMin());
        vacancy.setSalaryMax(vacancyDto.salaryMax());
        vacancy.setTown(vacancyDto.town());
        vacancy.setTags(tagsFrom(vacancyDto.tags()));
        baseInterviewRepository.deleteAll(vacancy.getBaseInterviews());
        vacancy.setBaseInterviews(null);

        var v = vacancyRepository.save(vacancy);
        List<BaseInterview> baseInterviews = from(vacancyDto.interviews(), vacancy);
        v.setBaseInterviews(baseInterviewRepository.saveAll(baseInterviews));
        return VacancyConvertor.vacancyDto(v);
    }

    @Override
    public VacancyDto getVacancy(long id) {
        return VacancyConvertor.vacancyDto(
                vacancyRepository.findById(id).orElseThrow(() -> noSuchVacancyException(id))
        );
    }

    @Override
    public List<VacancyDto> getVacancies(List<Long> ids) {
        var res = vacancyRepository.findAllById(ids);
        NotFoundExceptionFactory.containsAllOrThrow(
                res.stream().map(Vacancy::getId).toList(), ids,
                NotFoundExceptionFactory::noSuchVacanciesException);
        return VacancyConvertor.vacancyDtos(res);
    }
}

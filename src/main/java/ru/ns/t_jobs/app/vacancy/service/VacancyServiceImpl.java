package ru.ns.t_jobs.app.vacancy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.ns.t_jobs.app.interview.entity.InterviewBaseRepository;
import ru.ns.t_jobs.app.staff.entity.StaffRepository;
import ru.ns.t_jobs.app.tag.TagRepository;
import ru.ns.t_jobs.app.vacancy.dto.NewVacancyDto;
import ru.ns.t_jobs.app.vacancy.dto.VacancyConvertor;
import ru.ns.t_jobs.app.vacancy.dto.VacancyDto;
import ru.ns.t_jobs.app.vacancy.entity.Vacancy;
import ru.ns.t_jobs.app.vacancy.entity.VacancyRepository;

import java.util.List;

import static ru.ns.t_jobs.handler.exception.NotFoundExceptionFactory.noSuchVacancyException;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {

    private final VacancyRepository vacancyRepository;
    private final StaffRepository staffRepository;
    private final InterviewBaseRepository interviewBaseRepository;
    private final TagRepository tagRepository;

    @Override
    public List<VacancyDto> searchVacancies(String text, int page, int pageSize, int salaryLowerBound, List<Long> tagIds) {
        Pageable paging = PageRequest.of(page, pageSize);
        List<Vacancy> vacancies;
        if (tagIds == null || tagIds.isEmpty()) {
            vacancies = vacancyRepository.findAllByTags(salaryLowerBound, paging);
        } else {
            vacancies = vacancyRepository.findAllByTags(tagIds, tagIds.size(), salaryLowerBound, paging);
        }

        return vacancies.stream().filter(v -> v.getName().toLowerCase().contains(text)).map(VacancyConvertor::from).toList();
    }

    @Override
    public void createVacancy(NewVacancyDto vacancyDto) {
        vacancyRepository.save(createFrom(vacancyDto));
    }

    private Vacancy createFrom(NewVacancyDto v) {
        return Vacancy.builder()
                .name(v.name())
                .description(v.description())
                .salaryMin(v.salaryMin())
                .salaryMax(v.salaryMax())
                .town(v.town())
                .interviewBases(interviewBaseRepository.findAllById(v.interviews()))
                .tags(tagRepository.findAllById(v.tags()))
                .build();
    }

    @Override
    public void editVacancy(NewVacancyDto vacancyDto, long id) {
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> noSuchVacancyException(id));

        vacancy.setName(vacancyDto.name());
        vacancy.setDescription(vacancyDto.description());
        vacancy.setSalaryMin(vacancyDto.salaryMin());
        vacancy.setSalaryMax(vacancyDto.salaryMax());
        vacancy.setTown(vacancyDto.town());
        vacancy.setInterviewBases(interviewBaseRepository.findAllById(vacancyDto.interviews()));
        vacancy.setTags(tagRepository.findAllById(vacancyDto.tags()));
        vacancyRepository.save(vacancy);
    }
}

package ru.ns.t_jobs.app.vacancy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.ns.t_jobs.app.vacancy.dto.VacancyConvertor;
import ru.ns.t_jobs.app.vacancy.dto.VacancyDto;
import ru.ns.t_jobs.app.vacancy.entity.Vacancy;
import ru.ns.t_jobs.app.vacancy.entity.VacancyRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {

    private final VacancyRepository vacancyRepository;

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
}

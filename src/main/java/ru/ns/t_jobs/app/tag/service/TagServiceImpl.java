package ru.ns.t_jobs.app.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ns.t_jobs.app.tag.dto.TagConvertor;
import ru.ns.t_jobs.app.tag.dto.TagDto;
import ru.ns.t_jobs.app.tag.entity.TagRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    public List<TagDto> getAllTags() {
        var res = tagRepository.findAll();
        return TagConvertor.tagDtos(res);
    }
}

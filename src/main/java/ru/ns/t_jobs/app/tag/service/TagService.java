package ru.ns.t_jobs.app.tag.service;

import ru.ns.t_jobs.app.tag.dto.TagDto;

import java.util.List;

public interface TagService {
    List<TagDto> getAllTags();
}

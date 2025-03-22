package ru.ns.t_jobs.app.tag.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.ns.t_jobs.app.tag.dto.TagDto;
import ru.ns.t_jobs.app.tag.service.TagService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagControllerImpl implements TagController {

    private final TagService tagService;

    @Override
    public List<TagDto> getAllTags() {
        return tagService.getAllTags();
    }
}

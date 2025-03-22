package ru.ns.t_jobs.app.tag.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ns.t_jobs.app.tag.dto.TagDto;

import java.util.List;

@RequestMapping("/tags")
public interface TagController {
    @GetMapping("/all")
    List<TagDto> getAllTags();
}

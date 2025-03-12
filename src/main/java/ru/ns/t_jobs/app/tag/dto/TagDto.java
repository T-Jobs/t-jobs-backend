package ru.ns.t_jobs.app.tag.dto;

public record TagDto(
        long id,
        TagCategoryDto category,
        String name
) {
}

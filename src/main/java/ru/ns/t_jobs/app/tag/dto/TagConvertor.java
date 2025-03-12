package ru.ns.t_jobs.app.tag.dto;

import ru.ns.t_jobs.app.tag.entity.Tag;
import ru.ns.t_jobs.app.tag.entity.TagCategory;

import java.util.List;

public class TagConvertor {
    public static TagCategoryDto tagCategoryDto(TagCategory tc) {
        return new TagCategoryDto(tc.getId(), tc.getName());
    }

    public static TagDto tagDto(Tag t) {
        return new TagDto(
                t.getId(),
                tagCategoryDto(t.getCategory()),
                t.getName()
        );
    }

    public static List<TagDto> tagDtos(List<Tag> ts) {
        return ts.stream().map(TagConvertor::tagDto).toList();
    }
}

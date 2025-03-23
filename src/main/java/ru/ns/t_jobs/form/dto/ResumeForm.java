package ru.ns.t_jobs.form.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record ResumeForm(
        List<String> responses
) {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public LocalDateTime getDate() {
        String date = responses.getFirst();
        return LocalDateTime.parse(date, DATE_TIME_FORMATTER);
    }

    public Long getChatId() {
        return Long.valueOf(responses.get(1));
    }

    public String getName() {
        return responses.get(2);
    }

    public Integer getSalaryMin() {
        try {
            return Integer.valueOf(responses.get(3));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public String getDescription() {
        return responses.get(4);
    }

    public List<String> getTagNames() {
        return List.of(responses.get(5).split(","));
    }
}

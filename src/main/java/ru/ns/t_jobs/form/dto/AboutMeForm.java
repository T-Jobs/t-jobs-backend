package ru.ns.t_jobs.form.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record AboutMeForm (
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

    public String getSurname() {
        return responses.get(3).isBlank() ? null : responses().get(3);
    }

    public String getTown() {
        return responses.get(4).isBlank() ? null : responses().get(3);
    }
}

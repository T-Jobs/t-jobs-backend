package ru.ns.t_jobs.handler.exception;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class NotFoundExceptionFactory {

    public static NoSuchElementException noSuchCandidateException(long id) {
        return new NoSuchElementException("No candidate with %d id.".formatted(id));
    }

    public static NoSuchElementException noSuchCandidatesException(List<Long> ids) {
        return new NoSuchElementException("No candidates with %s ids.".formatted(ids));
    }

    public static NoSuchElementException noSuchVacancyException(long id) {
        return new NoSuchElementException("No vacancy with %d id.".formatted(id));
    }

    public static NoSuchElementException noSuchApplicationException(long cid, long vid) {
        return new NoSuchElementException("No application from candidate with %d id to vacancy with %d id."
                .formatted(cid, vid));
    }

    public static NoSuchElementException noSuchTrackException(long id) {
        return new NoSuchElementException("No tracks with %d id".formatted(id));
    }

    public static NoSuchElementException noSuchResumeException(long id) {
        return new NoSuchElementException("No resume with %d id".formatted(id));
    }

    public static NoSuchElementException noSuchBaseInterviewException(long id) {
        return new NoSuchElementException("No base interview with %d id".formatted(id));
    }

    public static NoSuchElementException noSuchBaseInterviewsException(List<Long> ids) {
        return new NoSuchElementException("No base interviews with %s ids.".formatted(ids));
    }

    public static NoSuchElementException noSuchInterviewException(long id) {
        return new NoSuchElementException("No interview with %d id.".formatted(id));
    }

    public static NoSuchElementException noSuchInterviewsException(List<Long> ids) {
        return new NoSuchElementException("No interviews with %s ids.".formatted(ids));
    }

    public static NoSuchElementException noSuchInterviewTypeException(long id) {
        return new NoSuchElementException("No interview type with %d id.".formatted(id));
    }

    public static NoSuchElementException noSuchStaffException(long id) {
        return new NoSuchElementException("No staff with %d id".formatted(id));
    }
}

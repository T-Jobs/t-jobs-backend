package ru.ns.t_jobs.handler.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class NotFoundExceptionFactory {

    // Candidate

    public static NoSuchElementException noSuchCandidateException(long id) {
        return new NoSuchElementException("No candidate with %d id.".formatted(id));
    }

    public static NoSuchElementException noSuchCandidatesException(List<Long> ids) {
        return new NoSuchElementException("No candidates with %s ids.".formatted(ids));
    }

    // Vacancy

    public static NoSuchElementException noSuchVacancyException(long id) {
        return new NoSuchElementException("No vacancy with %d id.".formatted(id));
    }

    public static NoSuchElementException noSuchVacanciesException(List<Long> ids) {
        return new NoSuchElementException("No vacancies with %s id.".formatted(ids));
    }

    // Application

    public static NoSuchElementException noSuchApplicationException(long cid, long vid) {
        return new NoSuchElementException("No application from candidate with %d id to vacancy with %d id."
                .formatted(cid, vid));
    }

    // Track

    public static NoSuchElementException noSuchTrackException(long id) {
        return new NoSuchElementException("No track with %d id.".formatted(id));
    }

    public static NoSuchElementException noSuchTracksException(List<Long> ids) {
        return new NoSuchElementException("No tracks with %s id.".formatted(ids));
    }

    // Resume

    public static NoSuchElementException noSuchResumeException(long id) {
        return new NoSuchElementException("No resume with %d id.".formatted(id));
    }

    public static NoSuchElementException noSuchResumesException(List<Long> ids) {
        return new NoSuchElementException("No resumes with %s ids.".formatted(ids));
    }

    // Base Interview

    public static NoSuchElementException noSuchBaseInterviewException(long id) {
        return new NoSuchElementException("No base interview with %d id.".formatted(id));
    }

    public static NoSuchElementException noSuchBaseInterviewsException(List<Long> ids) {
        return new NoSuchElementException("No base interviews with %s ids.".formatted(ids));
    }

    // Interview

    public static NoSuchElementException noSuchInterviewException(long id) {
        return new NoSuchElementException("No interview with %d id.".formatted(id));
    }

    public static NoSuchElementException noSuchInterviewsException(List<Long> ids) {
        return new NoSuchElementException("No interviews with %s ids.".formatted(ids));
    }

    // Interview Type

    public static NoSuchElementException noSuchInterviewTypeException(long id) {
        return new NoSuchElementException("No interview type with %d id.".formatted(id));
    }

    public static NoSuchElementException noSuchInterviewTypesException(List<Long> ids) {
        return new NoSuchElementException("No interview types with %s id.".formatted(ids));
    }

    // Staff

    public static NoSuchElementException noSuchStaffException(long id) {
        return new NoSuchElementException("No staff with %d id.".formatted(id));
    }

    public static NoSuchElementException noSuchStaffsException(List<Long> ids) {
        return new NoSuchElementException("No staffs with %s ids.".formatted(ids));
    }

    // Tag

    public static NoSuchElementException noSuchTagException(long id) {
        return new NoSuchElementException("No tag with %d id.".formatted(id));
    }

    public static NoSuchElementException noSuchTagsException(List<Long> ids) {
        return new NoSuchElementException("No such tags with %s ids.".formatted(ids));
    }

    // Other

    public static <E extends Throwable> void containsAllOrThrow(List<Long> l1, List<Long> l2, Function<List<Long>, E> f) throws E {
        var notFound = new ArrayList<>(l2);
        notFound.removeAll(l1);

        if (!notFound.isEmpty()) {
            throw f.apply(notFound);
        }
    }
}

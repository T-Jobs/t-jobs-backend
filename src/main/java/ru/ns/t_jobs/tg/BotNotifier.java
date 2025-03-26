package ru.ns.t_jobs.tg;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import ru.ns.t_jobs.app.candidate.entity.Candidate;
import ru.ns.t_jobs.app.candidate.entity.Resume;
import ru.ns.t_jobs.app.interview.entity.Interview;
import ru.ns.t_jobs.app.track.entity.Track;
import ru.ns.t_jobs.app.vacancy.entity.Vacancy;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class BotNotifier {
    private static final String TOKEN = "7569412835:AAHASFrKoOqw2TykMsRq-J67RpAl0-cRev4";
    private static final String URL_FORMAT = "https://api.telegram.org/bot" + TOKEN + "/sendMessage";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private static void sendMessage(long chatId, String message) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String jsonPayload = String.format("{\"chat_id\":\"%s\", \"text\":\"%s\", \"sparse_mode\": \"Markdown\"}", chatId, message);

            HttpPost postRequest = new HttpPost(URL_FORMAT);
            StringEntity entity = new StringEntity(jsonPayload, "UTF-8");
            postRequest.setEntity(entity);
            postRequest.setHeader("Content-Type", "application/json; charset=UTF-8");

            httpClient.execute(postRequest).close();
        } catch (IOException ignored) {
        }
    }

    public static void notifyCanceledInterview(Interview interview) {
        String message = """
                🚨 Уведомление! 🚨\\n\\n
                Секция '%s' для вакансии '%s' была отменена.\uD83E\uDDD1\u200D\uD83D\uDCBC
                Если у вас есть вопросы, не стесняйтесь обращаться! \uD83D\uDE0A""".formatted(
                interview.getInterviewType().getName(),
                interview.getTrack().getVacancy().getName()
        );
        sendMessage(interview.getTrack().getCandidate().getChatId(), message);
    }

    public static void notifySucceedInterview(Interview interview) {
        String message = """
                ✔ Интервью успешно пройдено! ✔\\n\\n
                Секция '%s' для вакансии '%s' была пройдена.
                Комментарий от интервьюера:
                > %s""".formatted(
                interview.getInterviewType().getName(),
                interview.getTrack().getVacancy().getName(),
                interview.getFeedback()
        );
        sendMessage(interview.getTrack().getCandidate().getChatId(), message);
    }

    public static void notifyFailedInterview(Interview interview) {
        String message = """
                ❌ Интервью завалено ❌\\n\\n
                Секция '%s' для вакансии '%s' была завалена. 😢
                Комментарий от интервьюера:
                > %s""".formatted(
                interview.getInterviewType().getName(),
                interview.getTrack().getVacancy().getName(),
                interview.getFeedback()
        );
        sendMessage(interview.getTrack().getCandidate().getChatId(), message);
    }

    public static void notifyDateInterview(Interview interview) {
        String message = """
                📝 Назначено интервью 📝\\n\\n
                Секция '%s' для вакансии '%s' будет проведена %s.
                """.formatted(
                interview.getInterviewType().getName(),
                interview.getTrack().getVacancy().getName(),
                interview.getDatePicked().format(DATE_TIME_FORMATTER)
        );
        sendMessage(interview.getTrack().getCandidate().getChatId(), message);
    }

    public static void notifyDateDeclined(Interview interview) {
        String message = """
                ⏰ Время проведение секции '%s' для вакансии '%s' было отменено. Требуется согласование.
                """.formatted(
                interview.getInterviewType().getName(),
                interview.getTrack().getVacancy().getName()
        );
        sendMessage(interview.getTrack().getCandidate().getChatId(), message);
    }

    public static void notifyApprovedApplication(Track track) {
        String message = """
                🎟 Заявка на отбор на вакансию '%s' была одобрена.
                """.formatted(
                track.getVacancy().getName()
        );
        sendMessage(track.getCandidate().getChatId(), message);
    }

    public static void notifyFinishedTrack(Track track) {
        String message = """
                🏁 Отбор на вакансию '%s' был завершен.
                """.formatted(
                track.getVacancy().getName()
        );
        sendMessage(track.getCandidate().getChatId(), message);
    }

    public static void notifyStartedTrack(Track track) {
        String message = """
                👩‍💻 Вас позвали на отбор на вакансию '%s'.
                """.formatted(
                track.getVacancy().getName()
        );
        sendMessage(track.getCandidate().getChatId(), message);
    }

    public static void notifyAboutMeFormAccepted(long chatId) {
        String message = "🔃 Гугл форма \"О себе\" успешна принята.";
        sendMessage(chatId, message);
    }

    public static void notifyResumeAccepted(Resume resume) {
        String message = """
                🔃 Резюме '%s было загружено.
                """.formatted(
                resume.getName()
        );
        sendMessage(resume.getCandidate().getChatId(), message);
    }

    public static void notifySuccessfullyApplied(long chatId, Vacancy vacancy) {
        String message = """
                💼 Заявка на вакансию '%s' была успешна подана.
                """.formatted(
                vacancy.getName()
        );
        sendMessage(chatId, message);
    }
}

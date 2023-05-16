package engine.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class CompletionDTO {
    @JsonProperty("id")
    long quizId;

    @JsonProperty("completedAt")
    LocalDateTime completionDate;

    public CompletionDTO(long quizId, LocalDateTime completionDate) {
        this.quizId = quizId;
        this.completionDate = completionDate;
    }

    public long getQuizId() {
        return quizId;
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }
}

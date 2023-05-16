package engine.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FeedbackDTO (@JsonProperty("success") boolean result, @JsonProperty("feedback") String message) {
}

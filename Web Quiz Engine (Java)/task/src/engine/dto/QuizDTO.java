package engine.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

public record QuizDTO (
        @JsonProperty
        long id,
        @JsonProperty
        @NotEmpty
        String title,
        @JsonProperty
        @NotEmpty
        String text,
        @JsonProperty
        @NotEmpty
        @Size (min = 2)
        List<String> options,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        Set<Integer> answer) {
}

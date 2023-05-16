package engine.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Completion {
    @Id
    @GeneratedValue
    private Long id;

    private String userName;

    private Long quizId;

    private LocalDateTime localDateTime;

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public void setDate(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public Long getQuizId() {
        return quizId;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}

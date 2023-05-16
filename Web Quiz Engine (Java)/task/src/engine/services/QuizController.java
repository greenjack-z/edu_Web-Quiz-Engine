package engine.services;

import engine.dto.AnswerDTO;
import engine.dto.FeedbackDTO;
import engine.dto.QuizDTO;
import engine.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
public class QuizController {
    private final QuizDBService quizDBService;
    private final UserDBService userDBService;

    PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(QuizController.class);

    @Autowired
    public QuizController(QuizDBService quizDBService, UserDBService userDBService, PasswordEncoder passwordEncoder) {
        this.quizDBService = quizDBService;
        this.userDBService = userDBService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/api/quizzes")
    public List<QuizDTO> getAllQuizzes() {
        logger.debug("REST get all");
        return quizDBService.getAllQuizzes();
    }

    @GetMapping("/api/quizzes/{id}")
    public QuizDTO getQuestion(@PathVariable long id) {
        logger.debug("REST get by id: {}", id);
        return quizDBService.getQuizById(id);
    }

    @PostMapping("/api/quizzes")
    public QuizDTO addQuiz(@Valid @RequestBody QuizDTO quizDTO) {
        logger.debug("REST add quiz");
        logger.debug("options: {}", quizDTO.options());
        logger.debug("answer: {}", quizDTO.answer());
        return quizDBService.getQuizById(quizDBService.addQuiz(quizDTO));
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public FeedbackDTO solve(@PathVariable int id, @Valid @RequestBody AnswerDTO answer) {
        logger.debug("REST solve");
        logger.debug("answer: {}", answer);
        return quizDBService.checkAnswer(id, answer);
    }

    @PostMapping("/api/register")
    public void addUser(@Valid @RequestBody UserDTO userDTO) {
        logger.debug("REST register");
        logger.debug("user: {}", userDTO);
        userDBService.addUser(new UserDTO(userDTO.email(), passwordEncoder.encode(userDTO.password())));
    }


    @DeleteMapping("/api/quizzes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuiz(@PathVariable long id) {
        quizDBService.deleteQuiz(id);
    }
}

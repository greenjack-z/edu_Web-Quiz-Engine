package engine.controllers;

import engine.dto.*;
import engine.services.CompletionDBService;
import engine.services.QuizDBService;
import engine.services.UserDBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@Validated
public class QuizController {
    private final QuizDBService quizDBService;
    private final UserDBService userDBService;
    private final CompletionDBService completionDBService;

    PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(QuizController.class);

    @Autowired
    public QuizController(QuizDBService quizDBService, UserDBService userDBService, CompletionDBService completionDBService, PasswordEncoder passwordEncoder) {
        this.quizDBService = quizDBService;
        this.userDBService = userDBService;
        this.completionDBService = completionDBService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/api/quizzes")
    public Page<QuizDTO> getQuizzesByPage(@RequestParam int page) {
        logger.debug("get all quizzes at page: {}", page);
        return quizDBService.getQuizzesByPage(page);
    }

    @GetMapping("/api/quizzes/{id}")
    public QuizDTO getQuestion(@PathVariable long id) {
        logger.debug("get quiz by quiz id: {}", id);
        return quizDBService.getQuizById(id);
    }

    @GetMapping("/api/quizzes/completed")
    public Page<CompletionDTO> getCompleted(@RequestParam int page) {
        logger.debug("get completed quizzes at page: {}", page);
        return completionDBService.getCompletionsPage(page);
    }

    @PostMapping("/api/quizzes")
    public QuizDTO addQuiz(@Valid @RequestBody QuizDTO quizDTO) {
        logger.debug("add new quiz");
        logger.debug("options: {}", quizDTO.options());
        logger.debug("answer: {}", quizDTO.answer());
        return quizDBService.getQuizById(quizDBService.addQuiz(quizDTO));
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public FeedbackDTO solve(@PathVariable int id, @Valid @RequestBody AnswerDTO answer) {
        logger.debug("solve quiz id: {}", id);
        logger.debug("answer: {}", answer);
        FeedbackDTO feedbackDTO = quizDBService.checkAnswer(id, answer);
        if (feedbackDTO.result()) {
            completionDBService.addCompletion(new CompletionDTO(id, LocalDateTime.now()));
        }
        return feedbackDTO;
    }

    @PostMapping("/api/register")
    public void addUser(@Valid @RequestBody UserDTO userDTO) {
        logger.debug("register new user");
        logger.debug("user: {}", userDTO);
        userDBService.addUser(new UserDTO(userDTO.email(), passwordEncoder.encode(userDTO.password())));
    }


    @DeleteMapping("/api/quizzes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuiz(@PathVariable long id) {
        logger.debug("delete quiz by id: {}", id);
        quizDBService.deleteQuiz(id);
    }
}

package engine.services;

import engine.dto.AnswerDTO;
import engine.dto.FeedbackDTO;
import engine.dto.QuizDTO;
import engine.exceptions.QuizNotFoundException;
import engine.entities.Quiz;
import engine.exceptions.WrongAuthorException;
import engine.repositories.QuizRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class QuizDBService {
    private final QuizRepository quizRepository;
    private final Logger logger = LoggerFactory.getLogger(QuizDBService.class);

    @Autowired
    public QuizDBService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public Page<QuizDTO> getQuizzesByPage(int page) {
        logger.debug("db search for quizzes at page: {}", page);
        Pageable pageable = PageRequest.of(page, 10);
        Page<Quiz> quizzesPage = quizRepository.findAll(pageable);
        return quizzesPage.map(quiz -> new QuizDTO(quiz.getId(), quiz.getTitle(), quiz.getText(), quiz.getOptions(), quiz.getAnswer()));
    }

    public QuizDTO getQuizById(long id) {
        logger.debug("db search quiz by id: {}", id);
        Quiz quiz = quizRepository.findById(id).orElseThrow(QuizNotFoundException::new);
        return new QuizDTO(quiz.getId(), quiz.getTitle(), quiz.getText(), quiz.getOptions(), quiz.getAnswer());
    }

    public FeedbackDTO checkAnswer(int id, AnswerDTO answerDTO) {
        logger.debug("checking answer: {}", answerDTO.answer());
        QuizDTO quizDTO = getQuizById(id);
        logger.debug("checking against: {}", quizDTO.answer());
        if (quizDTO.answer().equals(answerDTO.answer())) {
            logger.debug("answer = OK");
            return new FeedbackDTO(true, "Good job!");
        } else {
            logger.debug("answer = NOK");
            return new FeedbackDTO(false, "Try again.");
        }
    }

    public long addQuiz(QuizDTO quizDTO) {
        logger.debug("db get the author to add quiz");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();
        logger.debug("current email is: {}", currentEmail);
        Quiz quiz = new Quiz();
        quiz.setAuthor(currentEmail);
        quiz.setTitle(quizDTO.title());
        quiz.setText(quizDTO.text());
        quiz.setOptions(quizDTO.options());
        quiz.setAnswer(quizDTO.answer());
        logger.debug("db adding new quiz");
        Quiz savedQuiz = quizRepository.save(quiz);
        return savedQuiz.getId();
    }

    public void deleteQuiz(long id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(QuizNotFoundException::new);
        logger.debug("db check the author to delete quiz");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();
        logger.debug("current email is: {}", currentEmail);
        String authorEmail = quiz.getAuthor();
        logger.debug("author email is: {}", authorEmail);
        if (!currentEmail.equals(authorEmail)) {
            logger.debug("email is not valid");
            throw new WrongAuthorException();
        }
        logger.debug("delete quiz with id: {}", id);
        quizRepository.deleteById(id);
    }
}


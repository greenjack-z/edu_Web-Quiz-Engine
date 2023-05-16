package engine.services;

import engine.dto.CompletionDTO;
import engine.entities.Completion;
import engine.repositories.CompletionRepository;
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
public class CompletionDBService {
    private final CompletionRepository completionRepository;
    private final Logger logger = LoggerFactory.getLogger(CompletionDBService.class);

    @Autowired
    public CompletionDBService(CompletionRepository completionRepository) {
        this.completionRepository = completionRepository;
    }

    public Page<CompletionDTO> getCompletionsPage(int page) {
        logger.debug("get the user name for searching completions");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();
        logger.debug("username is: {}", currentEmail);
        Pageable pageable = PageRequest.of(page, 10);
        logger.debug("db search completions at page: {}", page);
        Page<Completion> completionPage = completionRepository.findAllByUserNameOrderByLocalDateTimeDesc(pageable, currentEmail);
        return completionPage.map(completion -> new CompletionDTO(completion.getQuizId(), completion.getLocalDateTime()));
    }

    public void addCompletion(CompletionDTO completionDTO) {
        logger.debug("get the user name for adding completions");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();
        logger.debug("username is: {}", currentEmail);
        Completion completion = new Completion();
        completion.setUserName(currentEmail);
        completion.setQuizId(completionDTO.getQuizId());
        completion.setDate(completionDTO.getCompletionDate());
        logger.debug("adding completion info");
        completionRepository.save(completion);
    }
}

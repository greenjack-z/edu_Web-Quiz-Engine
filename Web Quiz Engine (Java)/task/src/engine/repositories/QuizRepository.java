package engine.repositories;

import engine.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuizRepository extends PagingAndSortingRepository<Quiz, Long>, JpaRepository<Quiz, Long> {
}

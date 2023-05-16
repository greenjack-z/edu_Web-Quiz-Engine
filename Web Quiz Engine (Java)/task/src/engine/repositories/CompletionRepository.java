package engine.repositories;

import engine.entities.Completion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompletionRepository extends PagingAndSortingRepository<Completion, Long>, JpaRepository<Completion, Long> {
    Page<Completion> findAllByUserNameOrderByLocalDateTimeDesc(Pageable pageable, String userName);

}

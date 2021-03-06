package RS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import RS.domain.Replay;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface ReplayRepository extends JpaRepository<Replay, Long>, QueryDslPredicateExecutor<Replay> {

    Page<Replay> findAll(Pageable pageable);
    List<Replay> findByGameDate(Date date);
}

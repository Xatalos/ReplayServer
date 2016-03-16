package RS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import RS.domain.Replay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface ReplayRepository extends JpaRepository<Replay, Long> {

    Page<Replay> findAll(Pageable pageable);
}

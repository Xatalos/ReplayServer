package RS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import RS.domain.Replay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface ReplayRepository extends JpaRepository<Replay, Long> {

    Page<Replay> findAll(Pageable pageable);

    Page<Replay> findByNameContainingAndVersion(Pageable pageable, @Param("name") String name,
            @Param("version") String version);

    Page<Replay> findByNameContaining(Pageable pageable, @Param("name") String name);
}

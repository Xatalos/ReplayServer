package RS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import RS.domain.Replay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface ReplayRepository extends JpaRepository<Replay, Long> {

    Page<Replay> findAll(Pageable pageable);
    Page<Replay> findByVersion(Pageable pageable, @Param("version") String version);
    Page<Replay> findByArena(Pageable pageable, @Param("arena") String arena);
    Page<Replay> findByGameMode(Pageable pageable, @Param("gameMode") String gameMode);
    Page<Replay> findByVersionAndArena(Pageable pageable, @Param("version") String version,
            @Param("arena") String arena);
    Page<Replay> findByVersionAndGameMode(Pageable pageable, @Param("version") String version,
            @Param("gameMode") String gameMode);
    Page<Replay> findByGameModeAndArena(Pageable pageable, @Param("gameMode") String gameMode,
            @Param("arena") String arena);
    Page<Replay> findByVersionAndArenaAndGameMode(Pageable pageable, @Param("version") String version,
            @Param("arena") String arena, @Param("gameMode") String gameMode);
}

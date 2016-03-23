package RS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import RS.domain.Replay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface ReplayRepository extends JpaRepository<Replay, Long>, QueryDslPredicateExecutor<Replay> {

    @Override
    Page<Replay> findAll(Pageable pageable);
    Page<Replay> findByPlayers_Name(Pageable pageable, @Param("name") String name);
    Page<Replay> findByVersion(Pageable pageable, @Param("version") String version);
    Page<Replay> findByArena(Pageable pageable, @Param("arena") String arena);
    Page<Replay> findByGameMode(Pageable pageable, @Param("gameMode") String gameMode);
    Page<Replay> findByVersionAndArena(Pageable pageable, @Param("version") String version,
            @Param("arena") String arena);
    Page<Replay> findByVersionAndPlayers_Name(Pageable pageable, @Param("version") String version,
            @Param("name") String name);
    Page<Replay> findByVersionAndGameMode(Pageable pageable, @Param("version") String version,
            @Param("gameMode") String gameMode);
    Page<Replay> findByPlayers_NameAndGameMode(Pageable pageable, @Param("name") String name,
            @Param("gameMode") String gameMode);
    Page<Replay> findByGameModeAndArena(Pageable pageable, @Param("gameMode") String gameMode,
            @Param("arena") String arena);
    Page<Replay> findByPlayers_NameAndArena(Pageable pageable, @Param("name") String name,
            @Param("arena") String arena);
    Page<Replay> findByVersionAndArenaAndGameMode(Pageable pageable, @Param("version") String version,
            @Param("arena") String arena, @Param("gameMode") String gameMode);
    Page<Replay> findByPlayers_NameAndArenaAndGameMode(Pageable pageable, @Param("name") String name,
            @Param("arena") String arena, @Param("gameMode") String gameMode);
    Page<Replay> findByVersionAndPlayers_NameAndGameMode(Pageable pageable, @Param("version") String version,
            @Param("name") String name, @Param("gameMode") String gameMode);
    Page<Replay> findByVersionAndArenaAndPlayers_Name(Pageable pageable, @Param("version") String version,
            @Param("arena") String arena, @Param("name") String name);
    Page<Replay> findByVersionAndArenaAndGameModeAndPlayers_Name(Pageable pageable, 
            @Param("version") String version, @Param("arena") String arena, 
            @Param("gameMode") String gameMode, @Param("name") String name);
}

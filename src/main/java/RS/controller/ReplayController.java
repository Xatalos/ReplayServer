package RS.controller;

import RS.domain.Player;
import RS.domain.QReplay;
import RS.domain.Replay;
import RS.repository.PlayerRepository;
import RS.repository.ReplayRepository;
import RS.util.SearchResult;
import arkhados.replay.ReplayHeader;
import arkhados.replay.ReplayMetadataSerializer;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.expr.BooleanExpression;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ReplayController {

    @Autowired
    private ReplayRepository replayRepository;
    @Autowired
    private PlayerRepository playerRepository;

    @CrossOrigin // allows AJAX calls from other websites
    @RequestMapping(value = "/replays", method = RequestMethod.GET)
    public SearchResult getReplays() {
        Pageable pageable = new PageRequest(0, 30, Sort.Direction.DESC, "gameDate");
        Page<Replay> replayPage = replayRepository.findAll(pageable);
        return new SearchResult(replayPage.getContent());
    }

    @CrossOrigin
    @Transactional
    @RequestMapping(value = "/topreplays", method = RequestMethod.GET)
    public SearchResult topReplays(@RequestParam String sort) {
        Pageable pageable = new PageRequest(0, 30, Sort.Direction.DESC, "gameDate");
        if (sort.equals("downloads")) {
            pageable = new PageRequest(0, 30, Sort.Direction.DESC, "downloads");
        }
        Page<Replay> replayPage = replayRepository.findAll(pageable);
        return new SearchResult(replayPage.getContent());
    }

    @CrossOrigin
    @Transactional
    @RequestMapping(value = "/searchreplays", method = RequestMethod.GET)
    public SearchResult searchReplays(@RequestParam(required = false) String version,
            @RequestParam(required = false) String versionoperator,
            @RequestParam(value = "arena", required = false) String[] arenas,
            @RequestParam(value = "mode", required = false) String[] modes,
            @RequestParam(value = "player", required = false) String[] players) {
        QReplay replay = QReplay.replay;
        BooleanBuilder searchTerms = new BooleanBuilder();
        if (version != null && !version.isEmpty() && versionoperator != null && (versionoperator.equals("or-less") || versionoperator.equals("or-more"))) {
            if (versionoperator.equals("or-less")) {
                BooleanExpression hasCorrectVersion = replay.version.loe(version);
                searchTerms = searchTerms.and(hasCorrectVersion);
            } else if (versionoperator.equals("or-more")) {
                BooleanExpression hasCorrectVersion = replay.version.goe(version);
                searchTerms = searchTerms.and(hasCorrectVersion);
            }
        } else if (version != null && !version.isEmpty()) {
            BooleanExpression hasCorrectVersion = replay.version.eq(version);
            searchTerms = searchTerms.and(hasCorrectVersion);
        }
        BooleanBuilder modeChoices = new BooleanBuilder();
        if (modes != null) {
            for (String mode : modes) {
                if (!mode.isEmpty()) {
                    BooleanExpression hasCorrectGameMode = replay.gameMode.equalsIgnoreCase(mode);
                    modeChoices.or(hasCorrectGameMode);
                }
            }
        }
        searchTerms.andAnyOf(modeChoices);
        BooleanBuilder arenaChoices = new BooleanBuilder();
        if (arenas != null) {
            for (String arena : arenas) {
                if (!arena.isEmpty()) {
                    BooleanExpression hasCorrectArena = replay.arena.equalsIgnoreCase(arena);
                    arenaChoices.or(hasCorrectArena);
                }
            }
        }
        searchTerms.andAnyOf(arenaChoices);
        if (players != null) {
            for (String player : players) {
                if (!player.isEmpty()) {
                    BooleanExpression hasCorrectPlayer = replay.players.any().name.equalsIgnoreCase(player);
                    searchTerms.and(hasCorrectPlayer);
                }
            }
        }
        Iterable<Replay> tempReplays = replayRepository.findAll(searchTerms);
        List<Replay> replays = new ArrayList<Replay>();
        tempReplays.forEach(replays::add);
        return new SearchResult(replays);
    }

    @RequestMapping(value = "/newreplay", method = RequestMethod.POST)
    public void addReplay(@RequestParam("replay") MultipartFile file) throws IOException {
        if (!file.getOriginalFilename().contains(".rep")) {
            return;
        }

        ReplayMetadataSerializer ser = new ReplayMetadataSerializer();
        ReplayHeader header = ser.readObject(ByteBuffer.wrap(file.getBytes()), ReplayHeader.class);

        Replay replay = new Replay();
        replay.setContent("download_url");
        replay.setGameDate(header.getDate());
        replay.setGameMode(header.getGameMode());
        replay.setVersion(header.getVersion());
        replay.setArena(header.getArena());
        replay.setDownloads(0);
        replayRepository.save(replay);

        for (String playerName : header.getPlayers().values()) {
            Player player = new Player();
            player.setName(playerName);
            player.setReplay(replay);
            playerRepository.save(player);
        }
    }

    @RequestMapping(value = "/{id}/download", method = RequestMethod.POST)
    public void downloadReplay(@PathVariable Long id) {
        Replay replay = replayRepository.findOne(id);
        replay.setDownloads(replay.getDownloads() + 1);
        replayRepository.save(replay);
    }
}

package RS.controller;

import RS.domain.Replay;
import RS.repository.ReplayRepository;
import RS.util.SearchResult;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReplayRestController {

    @Autowired
    private ReplayRepository replayRepository;
    
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
    public SearchResult searchReplays(@RequestParam String name, @RequestParam String version) {
        Pageable pageable = new PageRequest(0, 30, Sort.Direction.DESC, "gameDate");
        Page<Replay> replayPage;
        if (version.equals("any")) {
            replayPage = replayRepository.findByNameContaining(pageable, name);
        } else {
            replayPage = replayRepository.findByNameContainingAndVersion(pageable, name, version);
        }
        return new SearchResult(replayPage.getContent());
    }
}

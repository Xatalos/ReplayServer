package RS.controller;

import RS.domain.Replay;
import RS.repository.ReplayRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("*")
public class ReplayController {

    @Autowired
    private ReplayRepository replayRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String listReplays(Model model) {
        model.addAttribute("replays", replayRepository.findAll());
        Pageable pageable = new PageRequest(0, 50, Sort.Direction.DESC, "gameDate");
        Page<Replay> riddlePage = replayRepository.findAll(pageable);
        model.addAttribute("replays", riddlePage.getContent());
        return "replays";
    }
    
    @RequestMapping(value = "/filterreplays", method = RequestMethod.GET)
    public String filterReplays(Model model, @RequestParam String sort) {
        model.addAttribute("replays", replayRepository.findAll());
        Pageable pageable = new PageRequest(0, 50, Sort.Direction.DESC, "gameDate");
        
        if (sort.equals("newest")) {
            pageable = new PageRequest(0, 50, Sort.Direction.DESC, "gameDate");
        } else if (sort.equals("downloads")) {
            pageable = new PageRequest(0, 50, Sort.Direction.DESC, "downloads");
        } else if (sort.equals("name")) {
            pageable = new PageRequest(0, 50, Sort.Direction.ASC, "name");
        }
        
        Page<Replay> riddlePage = replayRepository.findAll(pageable);
        model.addAttribute("replays", riddlePage.getContent());
        return "replays";
    }

    @RequestMapping(value = "/newreplay", method = RequestMethod.POST)
    public String addReplay(@RequestParam("replay") MultipartFile file, @Valid @ModelAttribute Replay replay, 
            BindingResult result, RedirectAttributes redirectAttributes) throws IOException {
        if(!file.getOriginalFilename().contains(".rep")){
            redirectAttributes.addFlashAttribute("notification", "You didn't add a proper replay file!");
            return "redirect:/";
        } else if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("notification", "You left some required text fields empty!");
            return "redirect:/";
        }
        replay.setGameDate(new Date().toString());
        replay.setContent(file.getBytes());
        ArrayList<String> players = new ArrayList<String>();
        players.add("testplayer1");
        players.add("testplayer2");
        replay.setPlayers(players);
        replay.setDownloads(new Random().nextInt(10)); // temporary solution
        replayRepository.save(replay);
        return "redirect:/";
    }
    
    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        Replay replay = replayRepository.findOne(id);
 
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + replay.getName());
        headers.setContentType(MediaType.parseMediaType("application/octet-stream"));
        
//        System.out.println(replay.getName());
//        System.out.println(replay.getDownloads());
//        int downloads = replay.getDownloads() + 1;
//        System.out.println(downloads);
//        replay.setDownloads(downloads);
//        System.out.println(replay.getDownloads());
        
        return new ResponseEntity<>(replay.getContent(), headers, HttpStatus.CREATED);
    }
}

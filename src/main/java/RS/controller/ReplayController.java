package RS.controller;

import RS.domain.Player;
import RS.domain.Replay;
import RS.repository.PlayerRepository;
import RS.repository.ReplayRepository;
import arkhados.replay.ReplayHeader;
import arkhados.replay.ReplayMetadataSerializer;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private PlayerRepository playerRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String listReplays(Model model) {
        Pageable pageable = new PageRequest(0, 30, Sort.Direction.DESC, "gameDate");
        Page<Replay> replayPage = replayRepository.findAll(pageable);
        model.addAttribute("replays", replayPage.getContent());
        return "replays";
    }

    @Transactional
    @RequestMapping(value = "/topreplaystl", method = RequestMethod.GET)
    public String topReplays(Model model, @RequestParam String sort) {
        Pageable pageable = new PageRequest(0, 30, Sort.Direction.DESC, "gameDate");
        if (sort.equals("downloads")) {
            pageable = new PageRequest(0, 30, Sort.Direction.DESC, "downloads");
        }
        Page<Replay> replayPage = replayRepository.findAll(pageable);
        model.addAttribute("replays", replayPage.getContent());
        return "replays";
    }

    @RequestMapping(value = "/newreplay", method = RequestMethod.POST)
    public String addReplay(@RequestParam("replay") MultipartFile file, @Valid @ModelAttribute Replay replay,
            BindingResult result, RedirectAttributes redirectAttributes) throws IOException {
        if (!file.getOriginalFilename().contains(".rep")) {
            redirectAttributes.addFlashAttribute("notification", "You didn't add a proper replay file!");
            return "redirect:/";
        } else if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("notification", "You left some required text fields empty!");
            return "redirect:/";
        }

        ReplayMetadataSerializer ser = new ReplayMetadataSerializer();
        ReplayHeader header = ser.readObject(ByteBuffer.wrap(file.getBytes()), ReplayHeader.class);

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

        return "redirect:/";
    }

    @RequestMapping(value = "/{id}/download", method = RequestMethod.POST)
    public String downloadReplay(@PathVariable Long id) {
        Replay replay = replayRepository.findOne(id);
        replay.setDownloads(replay.getDownloads() + 1);
        replayRepository.save(replay);
        return "redirect:/";
    }
}

package RS.controller;

import RS.domain.Replay;
import RS.repository.ReplayRepository;
import java.io.IOException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
        replay.setContent(file.getBytes());
        replayRepository.save(replay);
        return "redirect:/";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        Replay replay = replayRepository.findOne(id);
 
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + replay.getName());
        headers.setContentType(MediaType.parseMediaType("application/octet-stream"));
         
        return new ResponseEntity<>(replay.getContent(), headers, HttpStatus.CREATED);
    }
}

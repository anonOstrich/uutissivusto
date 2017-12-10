package wad.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import wad.domain.Article;
import wad.domain.ImageObject;
import wad.repository.ArticleRepository;
import wad.repository.ImageObjectRepository;

@Controller
public class UutisController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ImageObjectRepository imageObjectRepository;

    @GetMapping("/uutiset")
    public String home(Model model) {
        model.addAttribute("articles", articleRepository.findAll());
        return "index";
    }

    @Transactional
    @GetMapping("/uutiset/{id}")
    public String single(@PathVariable Long id, Model model) {
        if (id == null) {
            return "redirect:/uutiset";
        }
        Article article = articleRepository.findById(id).get();
        if (article == null) {
            return "redirect:/uutiset";
        }
        model.addAttribute("article", article);

        return "single";
    }

    @Transactional
    @PostMapping("/uutiset")
    public String add(@RequestParam String title, @RequestParam String lead, @RequestParam String mainText,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime published,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        if (!file.getContentType().contains("image")) {
            return "redirect:/uutiset";
        }
        ImageObject io = new ImageObject();
        io.setName(file.getOriginalFilename());
        io.setMediaType(file.getContentType());
        io.setSize(file.getSize());
        io.setContent(file.getBytes());
        imageObjectRepository.save(io);

        //old stuff
        Article article = new Article();
        article.setTitle(title);
        article.setLead(lead);
        article.setMainText(mainText);
        article.setPublished(published);
        article.setImage(io);
        articleRepository.save(article);

        return "redirect:/uutiset";
    }

    @GetMapping("/lisaa")
    public String creation() {
        return "creation";
    }

}

package wad.controller;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Article;
import wad.repository.ArticleRepository;

@Controller
public class UutisController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/uutiset")
    public String home(Model model) {
        model.addAttribute("articles", articleRepository.findAll());
        return "index";
    }
    
    @GetMapping("/uutiset/{id}")
    public String single(@PathVariable Long id, Model model){
        if(id == null){
            return "redirect:/uutiset";
        }
        Article article = articleRepository.findById(id); 
        if(article == null){
            return "redirect:/uutiset";
        }
        model.addAttribute("article", article);
        
        return "single";        
    }

    @PostMapping("/uutiset")
    public String add(@RequestParam String title, @RequestParam String lead, @RequestParam String mainText,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime published
    ) {
        Article article = new Article();
        article.setTitle(title);
        article.setLead(lead);
        article.setMainText(mainText);
        article.setPublished(published);
        articleRepository.save(article);

        
        return "redirect:/uutiset";
    }

    @GetMapping("/lisaa")
    public String creation() {
        return "creation";
    }
    
}

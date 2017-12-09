package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import wad.repository.ArticleRepository;

@Controller
public class UutisController {
    
    @Autowired
    private ArticleRepository articleRepository; 

    @GetMapping("/uutiset")
    public String home(Model model){
        model.addAttribute("articles", articleRepository.findAll());
        return "index";
    }
    
    
 
    
}

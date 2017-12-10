
package wad.controller;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import wad.domain.Article;
import wad.repository.ArticleRepository;


@Controller
public class DefaultController {
    
    @Autowired
    private ArticleRepository articleRepository; 
    

    
    
    @GetMapping("*")
    public String home(){
        return "redirect:/uutiset";
    }
    
}

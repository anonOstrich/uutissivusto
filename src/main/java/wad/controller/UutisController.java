package wad.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
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
import wad.domain.Category;
import wad.domain.ImageObject;
import wad.repository.ArticleRepository;
import wad.repository.CategoryRepository;
import wad.repository.ImageObjectRepository;

@Controller
public class UutisController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ImageObjectRepository imageObjectRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostConstruct
    public void init() {
        if (categoryRepository.findAll().isEmpty()) {
            Category category = new Category();
            category.setName("yhteiskunta");
            categoryRepository.save(category);
            category = new Category();
            category.setName("talous");
            categoryRepository.save(category);
            category = new Category();
            category.setName("viihde");
            categoryRepository.save(category);
        }
    }

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
            @RequestParam("file") MultipartFile file,
            @RequestParam(name = "category", required = false) Long[] categories
    ) throws IOException {

        if (!file.getContentType().contains("image")) {
            return "redirect:/uutiset";
        }

        if (categories == null) {
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
        
        Category category = null; 
        List<Category> articleCategories = new ArrayList(); 
        for (int i = 0; i < categories.length; i++){
            category = categoryRepository.findById(categories[i]).get();
            List<Article> articles = category.getArticles();
            if(!articles.contains(article)){
                articles.add(article);
            }
            category.setArticles(articles);
            articleCategories.add(category);
            categoryRepository.save(category);
        }
        article.setCategories(articleCategories);
        articleRepository.save(article);
        return "redirect:/uutiset";
    }

    @GetMapping("/lisaa")
    public String creation(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "creation";
    }

}

package wad.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import wad.domain.Account;
import wad.domain.Article;
import wad.domain.Category;
import wad.domain.ImageObject;
import wad.repository.AccountRepository;
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
    
    @Autowired
    private AccountRepository accountRepository; 

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
        article.addView();
        article = articleRepository.save(article);
        model.addAttribute("article", article);

        return "single";
    }

    @Transactional
    @PostMapping("/uutiset")
    public String add(@RequestParam String title, @RequestParam String lead, @RequestParam String mainText,
            @RequestParam("file") MultipartFile file,
            @RequestParam(name = "category", required = false) Long[] categories
    ) throws IOException {

        if (!file.getContentType().contains("image") || file.getContentType().contains("gif")) {
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
        article.setPublished(LocalDateTime.now());
        article.setImage(io);

        Category category = null;
        List<Category> articleCategories = new ArrayList();
        for (int i = 0; i < categories.length; i++) {
            category = categoryRepository.findById(categories[i]).get();
            List<Article> articles = category.getArticles();
            if (!articles.contains(article)) {
                articles.add(article);
            }
            category.setArticles(articles);
            articleCategories.add(category);
            categoryRepository.save(category);
        }
        article.setCategories(articleCategories);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        //lisätään käyttäjä
        List<Account> accounts = new ArrayList(); 
        accounts.add(accountRepository.findByUsername(username));
        article.setAccounts(accounts);               
        articleRepository.save(article);
        return "redirect:/uutiset";
    }

    @GetMapping("/lisaa")
    public String creation(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "creation";
    }

    @GetMapping("/uutiset/{id}/muokkaa")
    public String startModification(@PathVariable Long id, Model model) {
        Optional<Article> optArticle = articleRepository.findById(id);
        if (!optArticle.isPresent()) {
            return "redirect:/uutiset";
        }
        Article article = optArticle.get();
        model.addAttribute("article", article);
        model.addAttribute("categories", categoryRepository.findAll());
        return "modify";
    }

    @PostMapping("/uutiset/{id}/muokkaa")
    public String modify(@PathVariable Long id,
            @RequestParam String title, @RequestParam String lead, @RequestParam String mainText,
            @RequestParam(name = "category", required = false) Long[] categories
    ) {

        Optional<Article> optArticle = articleRepository.findById(id);

        if (!optArticle.isPresent()) {
            return "redirect:/uutiset";
        }
        Article article = optArticle.get();
        article.setTitle(title);
        article.setLead(lead);
        article.setMainText(mainText);

        //HUOM! kategorioiden tai kuvan muutosta ei vielä toteutettu. 
        article.setModified(LocalDateTime.now());
        articleRepository.save(article);

        return "redirect:/uutiset";
    }

}

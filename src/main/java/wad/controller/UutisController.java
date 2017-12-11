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
import org.springframework.security.access.annotation.Secured;
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
import wad.service.ArticleService;
import wad.service.CategoryService;

@Controller
public class UutisController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Transactional
    @GetMapping("/uutiset")
    public String home(Model model, @RequestParam(defaultValue = "viisiUusinta") String view, @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "desc") String direction) {
        articleService.addArticlesForFrontpage(model, view, category, direction);
        return "index";
    }

    @Transactional
    @GetMapping("/uutiset/{id}")
    public String single(@PathVariable Long id, Model model) {
        if (id == null) {
            return "redirect:/uutiset";
        }
        Optional<Article> optArticle = articleRepository.findById(id);
        if (!optArticle.isPresent()) {
            return "redirect:/uutiset";
        }
        Article article = optArticle.get();
        articleService.viewArticle(article);

        model.addAttribute("article", article);
        model.addAttribute("recentArticles", articleService.mostRecentArticles(5));
        model.addAttribute("popularArticles", articleService.mostPopularArticles(5));
        return "single";
    }

    @Transactional
    @PostMapping("/uutiset")
    public String add(@RequestParam String title, @RequestParam String lead, @RequestParam String mainText,
            @RequestParam("file") MultipartFile file,
            @RequestParam(name = "category", required = false) Long[] categories) throws IOException {
        if (!file.getContentType().contains("image") || file.getContentType().contains("gif")) {
            return "redirect:/uutiset";
        }
        if (categories == null) {
            return "redirect:/uutiset";
        }
        if (title.trim().isEmpty() || lead.trim().isEmpty() || mainText.trim().isEmpty()) {
            return "redirect:/uutiset";
        }
        articleService.createArticle(title, lead, mainText, file, categories);
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
        model.addAttribute("categories", categoryService.chosenCategories(article));
        return "modify";
    }

    @PostMapping("/uutiset/{id}/muokkaa")
    @Transactional
    public String modify(@PathVariable Long id,
            @RequestParam String title, @RequestParam String lead, @RequestParam String mainText,
            @RequestParam(name = "category", required = false) Long[] categories) {

        Optional<Article> optArticle = articleRepository.findById(id);
        if (id == null || !optArticle.isPresent() || categories == null || title.trim().isEmpty()
                || lead.trim().isEmpty() || mainText.trim().isEmpty()) {
            return "redirect:/uutiset";
        }
        articleService.updateArticle(optArticle.get(), title, lead, mainText, categories);
        return "redirect:/uutiset";
    }

}

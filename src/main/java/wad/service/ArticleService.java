package wad.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wad.domain.Account;
import wad.domain.Article;
import wad.domain.Category;
import wad.domain.ImageObject;
import wad.repository.AccountRepository;
import wad.repository.ArticleRepository;
import wad.repository.CategoryRepository;
import wad.repository.ImageObjectRepository;

@Service
public class ArticleService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageObjectRepository imageObjectRepository;

    public void viewArticle(Article article) {
        article.addView();
        article = articleRepository.save(article);
    }

    public void createArticle(String title, String lead, String mainText, MultipartFile file, Long[] categories) throws IOException {
        ImageObject io = new ImageObject();
        io.setName(file.getOriginalFilename());
        io.setMediaType(file.getContentType());
        io.setSize(file.getSize());
        io.setContent(file.getBytes());
        imageObjectRepository.save(io);

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
        List<Account> accounts = new ArrayList();
        accounts.add(accountRepository.findByUsername(username));
        article.setAccounts(accounts);
        articleRepository.save(article);
    }

    public void updateArticle(Article article, String title, String lead, String mainText, Long[] categories) {
        article.setTitle(title);
        article.setLead(lead);
        article.setMainText(mainText);
        article.setModified(LocalDateTime.now());
        
        List<Category> chosenCategories = new ArrayList();

        //katsotaan mitkä kaikki kategoriat on valittu, ja asetetaan näille artikkeliksi muokattu artikkeli. Näin varmistetaan, että ei ole kategoriaa
        // jolle ei olisi merkitty tätä artikkelia
        for (int i = 0; i < categories.length; i++) {
            Optional<Category> optCategory = categoryRepository.findById(categories[i]);
            if (!optCategory.isPresent()) {
                continue;
            }
            Category category = optCategory.get();
            
            
            List<Article> categoryArticles = category.getArticles();
            //toimiiko vain contains kuten halutaan? 
            if (!categoryArticles.contains(article)) {
                categoryArticles.add(article);
            }
            categoryRepository.save(category);
            chosenCategories.add(category);
        }

        //artikkeli saa luonnollisesti valitut kategoriat riippumatta siitä, mitä sillä oli aiemmin
        article.setCategories(chosenCategories);
        
        //jos on poistettu kategoria? 
        for(Category c: categoryRepository.findAll()){
            if(c.getArticles().contains(article) && !chosenCategories.contains(c)){
                c.getArticles().remove(article);
                categoryRepository.save(c); 
            }
        }
            
        articleRepository.save(article);
    }

}

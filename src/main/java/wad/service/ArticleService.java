package wad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wad.domain.Article;
import wad.repository.AccountRepository;
import wad.repository.ArticleRepository;

@Service
public class ArticleService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public void viewArticle(Article article) {
        article.addView();
        article = articleRepository.save(article);
    }

}

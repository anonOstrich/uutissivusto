package wad.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Article;
import wad.domain.Category;


public interface ArticleRepository extends JpaRepository<Article, Long> {
    
    
}

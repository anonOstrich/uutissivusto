
package wad.domain;

import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class ArticleTest {
    private Article article; 
    
    public ArticleTest() {
    }
    
    @Before
    public void setUp() {
        article = new Article(); 
    }

   
    
    @Test
    public void addingOneViewWorks(){
        article.addView(LocalDateTime.now()); 
        assertEquals(1, article.getWeeklyViews().size());
    }
    
    @Test
    public void addingMultipleViewsWorks(){
        for(int i = 0; i < 5; i++){
            article.addView(LocalDateTime.now()); 
        }
        assertEquals(5, article.getWeeklyViews().size());
    }
    
    @Test
    public void pruningOldViewsWorks(){
        article.addView(LocalDateTime.now().minusDays(10));
        article.addView();
        article.deleteViewsOlderThanWeek();
        assertEquals(1, article.getWeeklyViews().size());
    }
}

package wad.service;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wad.domain.Article;
import wad.domain.Category;
import wad.repository.CategoryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Before
    public void setUp() {
        
    }

    
    @Test 
    public void addCategoryAddsOneCategory(){
        categoryService.addCategory("elokuvat");
        assertEquals(1, categoryRepository.count());
    }
    
    @Test
    public void addCategoryDoesNotAddIfNameIsTaken(){
        Category category = new Category(); 
        category.setName("elokuvat");
        categoryRepository.save(category);
        categoryService.addCategory("elokuvat");
        assertEquals(1, categoryRepository.count());
    }
    
}

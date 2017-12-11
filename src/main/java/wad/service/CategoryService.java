package wad.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wad.domain.Article;
import wad.domain.Category;
import wad.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public void addCategory(String name) {
        name = name.trim().toLowerCase();
        Category category = categoryRepository.findByName(name);
        if (category == null) {
            category = new Category();
            category.setName(name);
            categoryRepository.save(category);
        }
    }

    //palauttaa listan kaikista kategorioista. Kategorian chosen-kenttä on true, jos kategoria liittyy artikkeliin. Muutoin se on false. 
    public List<Category> chosenCategories(Article article) {
        List<Category> categories = categoryRepository.findAll();
        List<Category> articleCategories = article.getCategories();
        for (Category category : categories) {
            if (articleCategories.contains(category)) {
                category.setChosen(true);
            } else {
                category.setChosen(false);
            }
        }
        return categories;
    }
}

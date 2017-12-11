package wad.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Category;
import wad.repository.CategoryRepository;
import wad.service.CategoryService;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/kategoriat")
    public String showCategories(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "categories";
    }

    @PostMapping("/kategoriat")
    public String addCategory(@RequestParam String categoryName) {

        if (categoryName != null && !categoryName.trim().isEmpty()) {
            categoryService.addCategory(categoryName);
        }

        return "redirect:/kategoriat";
    }

}

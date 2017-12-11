package wad.controller;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import wad.domain.Account;
import wad.domain.Category;
import wad.repository.AccountRepository;
import wad.repository.CategoryRepository;

@Controller
public class DefaultController {

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private CategoryRepository categoryRepository; 

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        Account account = accountRepository.findByUsername("esko");
        if (account != null) {
            return;
        }
        account = new Account();
        account.setUsername("esko");
        account.setPassword(passwordEncoder.encode("123"));
        accountRepository.save(account);

        account = new Account();
        account.setUsername("lilja");
        account.setPassword(passwordEncoder.encode("abc"));
        accountRepository.save(account);

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

    @GetMapping("*")
    public String home() {
        return "redirect:/uutiset";
    }

}

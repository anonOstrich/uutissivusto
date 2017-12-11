
package wad.controller;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import wad.domain.Account;
import wad.domain.Article;
import wad.repository.AccountRepository;
import wad.repository.ArticleRepository;


@Controller
public class DefaultController {
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder; 
    
    
    @PostConstruct
    public void init(){
        Account account = accountRepository.findByUsername("kayttaja");
        if(account != null){
            return;
        }
        account = new Account(); 
        account.setUsername("kayttaja");
        account.setPassword(passwordEncoder.encode("123"));
        accountRepository.save(account);
        
    }
    
    
    

    
    
    @GetMapping("*")
    public String home(){
        return "redirect:/uutiset";
    }
    
}


package wad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wad.repository.AccountRepository;
import wad.repository.ArticleRepository;

@Service
public class UutisService {
    
    @Autowired
    private AccountRepository accountRepository; 
    
    @Autowired
    private ArticleRepository articleRepository; 
    
    
    
}

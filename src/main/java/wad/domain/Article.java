
package wad.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List; 
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;

/*
TODO: kuvan lisääminen! Voiko yksi kuva olla monessa artikkelissa? Ei, vaikka toki samansisältöinen kuva voi.
*/

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article extends AbstractPersistable<Long>{

    @ManyToMany
    private List<Category> categories;
    private String title; 
    private String lead; //ingressi
    private String mainText; 
    private LocalDateTime published; 
    
    @OneToOne
    @Lob
    private ImageObject image; 
    
    @ManyToMany
    private List<Account> accounts; 
    

    
}

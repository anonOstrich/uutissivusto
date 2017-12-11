
package wad.domain;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import java.util.List; 
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category extends AbstractPersistable<Long> {
    private String name; 
    @ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER)
    private List<Article> articles;
    // Käytetään vain uutisen muokkaamisessa - uutisella olevat kategoriat on valmiiksi rastitettu 
    private boolean chosen;
    
   @Override
   public boolean equals(Object o){
    if (o == null || o.getClass() != this.getClass()){
        return false; 
    }
        Category verrattava = (Category) o;
        return this.name.equals(verrattava.getName());
   }
}

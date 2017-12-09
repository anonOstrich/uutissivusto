
package wad.domain;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import java.util.List; 
import javax.persistence.ManyToMany;
import wad.domain.Article; 


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category extends AbstractPersistable<Long> {
    private String name; 
    @ManyToMany(mappedBy = "categories")
    private List<Article> articles; 
}

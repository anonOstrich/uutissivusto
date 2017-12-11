package wad.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
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
public class Article extends AbstractPersistable<Long> {

    @ManyToMany
    private List<Category> categories;
    private String title;
    private String lead; //ingressi
    private String mainText;
    private LocalDateTime published;
    private LocalDateTime modified;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<LocalDateTime> weeklyViews;

    @OneToOne
    @Lob
    private ImageObject image;

    @ManyToMany
    private List<Account> accounts;
       
   
    public void addView(){
        this.addView(LocalDateTime.now());
    }

    public void addView(LocalDateTime time) {
        if (weeklyViews == null) {
            weeklyViews = new ArrayList();
        }

        weeklyViews.add(time);
    }
    
    public void deleteViewsOlderThanWeek(){
        this.deleteViewsOlderThan(LocalDateTime.now().minusWeeks(1));
    }

    
    //no need to be public, even for testing purposes? 
    public void deleteViewsOlderThan(LocalDateTime time) {
        if (weeklyViews != null) {
            List<LocalDateTime> deleted = new ArrayList();
            for (LocalDateTime dt : weeklyViews) {
                if (dt.isBefore(time)) {
                    deleted.add(dt);
                } else {
                    break;
                }
            }
            weeklyViews.removeAll(deleted);
        }
    }
    
    public int getNumberOfViews(){
        if (this.weeklyViews == null) return 0; 
        return this.weeklyViews.size(); 
    }

}

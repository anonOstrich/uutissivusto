package wad.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.hibernate.annotations.Type;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article extends AbstractPersistable<Long> {

    @ManyToMany
    private List<Category> categories;
    private String title;
    @Type(type = "text")
    private String lead; //ingressi
    @Type(type = "text")
    private String mainText;
    private LocalDateTime published;
    private LocalDateTime modified;    
    @ElementCollection(fetch = FetchType.LAZY)
    private List<LocalDateTime> weeklyViews;
    private int viewCount; 
    @OneToOne
    @Lob
    @Basic(fetch = FetchType.LAZY)
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
        viewCount = weeklyViews.size(); 
    }
    
    public void deleteViewsOlderThanWeek(){
        this.deleteViewsOlderThan(LocalDateTime.now().minusWeeks(1));
    }

    
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
            viewCount -= deleted.size(); 
        }
    }
    

}

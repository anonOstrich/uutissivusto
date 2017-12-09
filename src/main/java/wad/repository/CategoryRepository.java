
package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Category;


public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}

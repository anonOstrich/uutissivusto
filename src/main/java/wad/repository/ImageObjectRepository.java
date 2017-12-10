
package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.ImageObject;


public interface ImageObjectRepository extends JpaRepository<ImageObject, Long> {
    ImageObject findById(Long id);
    
}

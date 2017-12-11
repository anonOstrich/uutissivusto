package wad.controller;

import java.io.IOException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import wad.domain.ImageObject;
import wad.repository.ImageObjectRepository;
import wad.service.ImageService;

@Controller
public class ImageController {

    @Autowired
    private ImageObjectRepository imageObjectRepository;
    
    @Autowired
    private ImageService imageService; 

    @GetMapping("/images")
    public String showAddPage(Model model) {
        model.addAttribute("images", imageObjectRepository.findAll());
        return "images";
    }


    @Transactional
    @GetMapping("/images/{id}")
    public ResponseEntity<byte[]> viewFile(@PathVariable Long id) {
        if (imageObjectRepository.findById(id).isPresent()) {
            return imageService.returnImageFile(id);       
        }
        
        //not optimal... Will null cause an error? Should return null object (mock ResponseEntity) instead? 
        return null; 
    }

}

package wad.controller;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        return null;
    }

}

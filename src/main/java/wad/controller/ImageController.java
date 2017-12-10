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

@Controller
public class ImageController {

    @Autowired
    private ImageObjectRepository imageObjectRepository;

    @GetMapping("/images")
    public String showAddPage(Model model) {
        model.addAttribute("images", imageObjectRepository.findAll());
        return "images";
    }

    @PostMapping("/images")
    public String add(@RequestParam("file") MultipartFile file) throws IOException{
        if(!file.getContentType().contains("image")){
            return "redirect:/images";
        }
        ImageObject io = new ImageObject(); 
        io.setName(file.getOriginalFilename());
        io.setMediaType(file.getContentType());
        io.setSize(file.getSize());
        io.setContent(file.getBytes());
        imageObjectRepository.save(io);        
        return "redirect:/images";
    }

    @Transactional
    @GetMapping("/images/{id}")
    public ResponseEntity<byte[]> viewFile(@PathVariable Long id) {

        ImageObject fo = imageObjectRepository.findOne(id);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(fo.getMediaType()));
        headers.setContentLength(fo.getSize());

        return new ResponseEntity<>(fo.getContent(), headers, HttpStatus.CREATED);
    }

}

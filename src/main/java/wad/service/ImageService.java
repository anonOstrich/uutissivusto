package wad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import wad.domain.ImageObject;
import wad.repository.ImageObjectRepository;

@Service
public class ImageService {

    @Autowired
    private ImageObjectRepository imageRepository;

    public ResponseEntity<byte[]> returnImageFile(Long id) {
        ImageObject fo = imageRepository.findById(id).get();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(fo.getMediaType()));
        headers.setContentLength(fo.getSize());
        return new ResponseEntity<>(fo.getContent(), headers, HttpStatus.CREATED);

    }
    


}

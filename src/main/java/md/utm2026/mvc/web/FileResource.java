package md.utm2026.mvc.web;

import md.utm2026.mvc.service.FileStorageService;
import md.utm2026.mvc.service.dto.FileUploadResponseDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file")
public class FileResource {

    private final FileStorageService fileStorageService;

    public FileResource(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> upload(@RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(fileStorageService.store(file));
    }
}

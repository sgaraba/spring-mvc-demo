package md.utm2026.mvc.web;

import md.utm2026.mvc.service.FileStorageService;
import md.utm2026.mvc.service.dto.FileUploadResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/upload")
public class FileUploadPageController {

    private final FileStorageService fileStorageService;

    public FileUploadPageController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping
    public String viewForm() {
        return "upload";
    }

    @PostMapping
    public String handleUpload(@RequestParam("file") MultipartFile file, Model model) {
        try {
            FileUploadResponseDto response = fileStorageService.store(file);
            model.addAttribute("upload", response);
        } catch (ResponseStatusException exception) {
            String message = exception.getReason() != null ? exception.getReason() : "Nu pot salva fisierul.";
            model.addAttribute("error", message);
        }
        return "upload";
    }
}

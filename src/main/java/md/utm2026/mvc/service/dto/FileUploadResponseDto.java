package md.utm2026.mvc.service.dto;

public record FileUploadResponseDto(String originalName, String storedName, long sizeBytes) {
}

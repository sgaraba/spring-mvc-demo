package md.utm2026.mvc.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestUserDto(
        @NotBlank String username,
        @NotNull Integer age
) {
}

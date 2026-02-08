package md.utm2026.mvc.converter;

import md.utm2026.mvc.service.dto.RequestUserDto;
import org.springframework.core.convert.converter.Converter;

public class StringToRequestUserDtoConverter implements Converter<String, RequestUserDto> {

    @Override
    public RequestUserDto convert(String source) {
        if (source == null || source.isBlank()) {
            throw new IllegalArgumentException("filter must not be blank. Expected format: username:age");
        }
        String[] parts = source.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("filter must be in format username:age");
        }
        String username = parts[0].trim();
        String ageText = parts[1].trim();
        if (username.isBlank()) {
            throw new IllegalArgumentException("username must not be blank");
        }
        if (ageText.isBlank()) {
            throw new IllegalArgumentException("age must not be blank");
        }
        Integer age;
        try {
            age = Integer.valueOf(ageText);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("age must be a number", ex);
        }
        return new RequestUserDto(username, age);
    }
}

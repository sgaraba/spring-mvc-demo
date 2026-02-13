package md.utm2026.mvc.converter;

import md.utm2026.mvc.service.dto.UserId;
import org.springframework.core.convert.converter.Converter;

public class StringToUserIdConverter implements Converter<String, UserId> {

    @Override
    public UserId convert(String source) {
        if (source == null || source.isBlank()) {
            return null;
        }
        return new UserId(Long.valueOf(source));
    }
}

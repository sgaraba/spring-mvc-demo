package md.utm2026.mvc.config;

import md.utm2026.mvc.converter.StringToUserIdConverter;
import md.utm2026.mvc.converter.StringToRequestUserDtoConverter;
import md.utm2026.mvc.formatter.LocalDateDashFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToUserIdConverter());
        registry.addConverter(new StringToRequestUserDtoConverter());
        registry.addFormatter(new LocalDateDashFormatter());
    }
}

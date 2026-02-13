package md.utm2026.mvc.config;

import md.utm2026.mvc.converter.StringToRequestUserDtoConverter;
import md.utm2026.mvc.converter.StringToUserIdConverter;
import md.utm2026.mvc.formatter.LocalDateDashFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableAsync
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final RequestLoggingInterceptor requestLoggingInterceptor;

    public WebMvcConfig(RequestLoggingInterceptor requestLoggingInterceptor) {
        this.requestLoggingInterceptor = requestLoggingInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(requestLoggingInterceptor)
                .addPathPatterns("/api/user/**");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToUserIdConverter());
        registry.addConverter(new StringToRequestUserDtoConverter());

        registry.addFormatter(new LocalDateDashFormatter());
    }
}

package md.utm2026.mvc.formatter;

import org.springframework.format.Formatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateDashFormatter implements Formatter<LocalDate> {

    private static final DateTimeFormatter FORMATTER_IN = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter FORMATTER_OUT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public LocalDate parse(String text, Locale locale) {
        return LocalDate.parse(text, FORMATTER_IN);
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return FORMATTER_OUT.format(object);
    }
}

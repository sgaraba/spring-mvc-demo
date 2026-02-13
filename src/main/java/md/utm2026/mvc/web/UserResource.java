package md.utm2026.mvc.web;

import jakarta.validation.Valid;
import md.utm2026.mvc.exception.UserNotFoundException;
import md.utm2026.mvc.service.dto.RequestUserDto;
import md.utm2026.mvc.service.dto.UserId;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserResource {

    private static final Logger logger = LoggerFactory.getLogger(UserResource.class);

    private final ConversionService conversionService;

    public UserResource(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @PostMapping
    public ResponseEntity<RequestUserDto> create(@Valid @RequestBody RequestUserDto requestUserDto) throws Exception {
        return ResponseEntity
                .created(new URI("/api/user/1"))
                .body(new RequestUserDto(requestUserDto.username(), requestUserDto.age()));
    }

    @GetMapping()
    public ResponseEntity<List<RequestUserDto>> getUserList() {
        return ResponseEntity.ok(List.of(new RequestUserDto("Petru", 55)));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<RequestUserDto> getUserById(@PathVariable UserId userId) {
        logger.info("UserId convert implicit de Spring : {}",  userId);
        UserId convertManual = conversionService.convert(userId, UserId.class);

        logger.info("UserId convertManual : {}",  convertManual);

        if (userId.value() != 1) {
            throw new UserNotFoundException(userId.value());
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-TEST", "Test123");
        return new ResponseEntity<>(new RequestUserDto("Petru", 55), headers, HttpStatus.OK);
        //ResponseEntity.ok(new RequestUserDto("Petru", 55));
    }

    @GetMapping("/by-filter")
    public ResponseEntity<RequestUserDto> getByFilter(@RequestParam RequestUserDto filter)  {
        logger.info("Request to user by filter {}.", filter);
        return ResponseEntity.ok(filter);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<RequestUserDto> getDeleteById() {
        return ResponseEntity.noContent().build();
    }
}

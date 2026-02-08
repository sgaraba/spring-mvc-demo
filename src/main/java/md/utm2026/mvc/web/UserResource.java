package md.utm2026.mvc.web;

import md.utm2026.mvc.service.dto.UserId;
import md.utm2026.mvc.service.dto.RequestUserDto;
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

    @PostMapping
    public ResponseEntity<RequestUserDto> create(@RequestBody RequestUserDto requestUserDto) throws Exception {
        return ResponseEntity
                .created(new URI("/api/user/1"))
                .body(new RequestUserDto("Petru", 55));
    }

    @GetMapping()
    public ResponseEntity<List<RequestUserDto>> getUserList()  {
        return ResponseEntity.ok(List.of(new RequestUserDto("Petru", 55)));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<RequestUserDto> getUserById(@PathVariable UserId userId)  {
        logger.info("Request to user by id {}.", userId.value());
        return ResponseEntity.ok(new RequestUserDto("Petru", 55));
    }

    @GetMapping("/by-filter")
    public ResponseEntity<RequestUserDto> getByFilter(@RequestParam RequestUserDto filter)  {
        logger.info("Request to user by filter {}.", filter);
        return ResponseEntity.ok(filter);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<RequestUserDto> getDeleteById()  {
        return ResponseEntity.noContent().build();
    }
}

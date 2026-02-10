package md.utm2026.mvc.web;

import jakarta.validation.Valid;
import md.utm2026.mvc.exception.UserNotFoundException;
import md.utm2026.mvc.service.dto.RequestUserDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserResource {

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
    public ResponseEntity<RequestUserDto> getUserById(@PathVariable Long userId) {
        if (userId != 1) {
            throw new UserNotFoundException(userId);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-TEST", "Test123");
        return new ResponseEntity<>(new RequestUserDto("Petru", 55), headers, HttpStatus.OK);
        //ResponseEntity.ok(new RequestUserDto("Petru", 55));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<RequestUserDto> getDeleteById() {
        return ResponseEntity.noContent().build();
    }
}

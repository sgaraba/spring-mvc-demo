package md.utm2026.mvc.web;

import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import md.utm2026.mvc.validation.DiffOrder;

@RestController
@RequestMapping("/api/calc")
@Validated
public class CalculatorResource {

    @GetMapping("/sum/p1/{p1}/p2/{p2}")
    public Integer sum(@PathVariable @NotNull @Min(0) Integer p1,
                       @PathVariable @NotNull @Min(0) Integer p2) {
        return p1 + p2;
    }

    @GetMapping("/diff")
    @DiffOrder
    public Integer diff(@RequestParam @NotNull @Min(0) Integer p1,
                        @RequestParam @NotNull @Min(0) Integer p2) {
        return p1 - p2;
    }
}

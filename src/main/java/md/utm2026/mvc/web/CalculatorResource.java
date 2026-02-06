package md.utm2026.mvc.web;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calc")
public class CalculatorResource {


    @GetMapping("/sum/p1/{p1}/p2/{p2}")
    public Integer sum(@PathVariable Integer p1, @PathVariable Integer p2) {
        return p1 + p2;
    }

    @GetMapping("/diff")
    public Integer diff(@RequestParam Integer p1, @RequestParam Integer p2) {
        return p1 - p2;
    }
}

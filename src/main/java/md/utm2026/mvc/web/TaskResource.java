package md.utm2026.mvc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;

@Controller
@ResponseBody
@RequestMapping("/api/task")
public class TaskResource {

    @GetMapping("/v1")
    public String testV1(){
        return "Hello task v1";
    }

    @GetMapping("/v2")
    public String testV2(){
        return "Hello task v2";
    }

    @GetMapping("/by-date")
    public String getByDate(@RequestParam LocalDate date) {
        return "Task date: " + date;
    }
}

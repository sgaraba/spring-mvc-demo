package md.utm2026.mvc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}

package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class indexController {

    @RequestMapping({"","/","/index"})
    public String getIndexPage(){
        System.out.println("Some message to say ...123");
        return "index";
    }
}

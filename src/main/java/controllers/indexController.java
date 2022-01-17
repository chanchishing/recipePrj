package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class indexController {

    @RequestMapping({"","/","/index"})
    public String getIndexPage(){
        System.out.println("Some mesage to say ... 123456");
        return "index";
    }
}

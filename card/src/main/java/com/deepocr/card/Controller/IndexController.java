package com.deepocr.card.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/task")
public class IndexController {
    public ModelAndView index(){
        return new ModelAndView("index");
    }
}

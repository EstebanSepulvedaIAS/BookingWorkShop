package com.app.reserve.shared.infrastructure.api;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OpenApiController {

    @GetMapping("/")
    @Parameter(hidden = true)
    public ModelAndView index(){
        return new ModelAndView("redirect:/swagger-ui.html");
    }
}

package mezo.restmvc.spring_6_rest_mvc.controllers;


import lombok.AllArgsConstructor;
import mezo.restmvc.spring_6_rest_mvc.service.JuiceService;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class JuiceController {
    private final JuiceService juiceService;
}

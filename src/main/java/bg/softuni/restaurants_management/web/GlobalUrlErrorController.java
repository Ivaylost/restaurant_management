package bg.softuni.restaurants_management.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GlobalUrlErrorController implements ErrorController {
    @RequestMapping("/error")
    public ModelAndView handleError() {
        return new ModelAndView("object-not-found");
    }
}

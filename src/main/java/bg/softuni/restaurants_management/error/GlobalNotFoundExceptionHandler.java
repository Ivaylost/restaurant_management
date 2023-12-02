package bg.softuni.restaurants_management.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalNotFoundExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ModelAndView handlePageNotFound(ObjectNotFoundException exception){
        ModelAndView view = new ModelAndView("object-not-found");
        view.addObject("message", exception.getMessage());
        return view;
    }
}

package ee.ttu.t031687.web.controllers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

public class AbstractController {
    
    @ExceptionHandler
    public @ResponseBody Result error(Exception ex) {
        ex.printStackTrace();
        return Result.fail(ex.getClass().getSimpleName() + ": " + ex.getMessage());
    }

}

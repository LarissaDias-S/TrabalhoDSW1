package trabalho.dsw1.vagas.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ErrorViewController implements ErrorViewResolver {

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("status", status.value());
        mav.addObject("timestamp", model.get("timestamp"));
        
        switch (status.value()) {
            case 403:
                mav.addObject("error", "error.403.title");
                mav.addObject("message", "error.403.message");
                break;
            case 404:
                mav.addObject("error", "error.404.title");
                mav.addObject("message", "error.404.message");
                break;
            case 500:
                mav.addObject("error", "error.500.title");
                mav.addObject("message", "error.500.message");
                break;
            default:
                mav.addObject("error", "error.default.title");
                mav.addObject("message", "error.default.message");
                break;
        }
        return mav;
    }
}
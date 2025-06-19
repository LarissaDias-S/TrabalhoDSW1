package trabalho.dsw1.vagas.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
        public String login() 
        {
            return "login"; 
        }

    @PostMapping("/login")
    public String login(@RequestParam Map<String, String> form) 
    {
        System.out.println("Dados de login recebidos:");
        form.forEach((k, v) -> System.out.println(k + ": " + v));
        return "redirect:/";
    }
    
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; 
    }

    
}

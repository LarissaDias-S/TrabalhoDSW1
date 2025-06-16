package trabalho.dsw1.vagas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@Controller
public class VagasApplicationController 
{
    @PostMapping("/login")
    public String login(@RequestParam Map<String, String> form) 
    {
        System.out.println("Dados de login recebidos:");
        form.forEach((k, v) -> System.out.println(k + ": " + v));
        return "redirect:/";
    }

    @GetMapping("/cadastro")
    public String cadastro() 
    {
        return "cadastro"; 
    }

    @PostMapping("/cadastro")
    public String cadastro(@RequestParam Map<String, String> form) 
    {
        System.out.println("Dados recebidos:");
        form.forEach((k, v) -> System.out.println(k + ": " + v));
        
        return "redirect:/cadastro-sucesso"; 
    }

    @GetMapping("/cadastro-sucesso")
    public String cadastroSucesso() 
    {
        return "cadastro-sucesso"; 
    }

    @GetMapping("/")
    public String index() 
    {
        return "index"; 
    }
    
    @GetMapping("/login")
    public String login() 
    {
        return "login"; 
    }

    @GetMapping("/vagas")
    public String vagas() {
        return "vagas";
    }
    
}

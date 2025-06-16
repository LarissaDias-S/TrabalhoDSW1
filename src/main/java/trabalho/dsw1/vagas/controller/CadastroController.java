package trabalho.dsw1.vagas.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CadastroController {

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
    
}

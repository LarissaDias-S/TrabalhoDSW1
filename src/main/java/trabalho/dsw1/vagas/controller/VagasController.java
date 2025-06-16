package trabalho.dsw1.vagas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VagasController {

    @GetMapping("/vagas")
    public String vagas() {
        return "vagas";
    }
    
}

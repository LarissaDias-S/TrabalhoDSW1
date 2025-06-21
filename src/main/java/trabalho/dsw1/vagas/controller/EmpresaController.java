package trabalho.dsw1.vagas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//home quando loga como empresa
@Controller
@RequestMapping("/empresa")
public class EmpresaController {

    @GetMapping("/home")
    public String homeEmpresa() {
        return "empresa-home";
    }
}
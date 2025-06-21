package trabalho.dsw1.vagas.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import trabalho.dsw1.vagas.model.Empresa;
import trabalho.dsw1.vagas.model.Profissional;
import trabalho.dsw1.vagas.repository.EmpresaRepository;
import trabalho.dsw1.vagas.repository.ProfissionalRepository;

@Controller
public class CadastroController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @GetMapping("/cadastro")
    public String cadastro() {
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastro(@RequestParam Map<String, String> form) {
        String tipo = form.get("tipo"); // "empresa" ou "profissional"

        if ("empresa".equals(tipo)) {
            Empresa empresa = new Empresa();
            empresa.setNome(form.get("nome"));
            empresa.setEmail(form.get("email"));
            empresa.setSenha(encoder.encode(form.get("senha")));
            empresaRepository.save(empresa);
        } else if ("profissional".equals(tipo)) {
            Profissional profissional = new Profissional();
            profissional.setNome(form.get("nome"));
            profissional.setEmail(form.get("email"));
            profissional.setSenha(encoder.encode(form.get("senha")));
            profissionalRepository.save(profissional);
        }

        return "redirect:/cadastro-sucesso";
    }

    @GetMapping("/cadastro-sucesso")
    public String cadastroSucesso() {
        return "cadastro-sucesso";
    }
}

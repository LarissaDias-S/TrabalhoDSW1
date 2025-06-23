package trabalho.dsw1.vagas.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;
import trabalho.dsw1.vagas.domain.Empresa;
import trabalho.dsw1.vagas.domain.Vaga;
import trabalho.dsw1.vagas.service.impl.VagaService;
import trabalho.dsw1.vagas.service.EmpresaService;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {

    private final VagaService vagaService;
    private final EmpresaService empresaService;

    public EmpresaController(VagaService vagaService, EmpresaService empresaService) {
        this.vagaService = vagaService;
        this.empresaService = empresaService;
    }

    @GetMapping("/home")
    public String homeEmpresa() {
        return "empresas/empresa-home";
    }

    @GetMapping("/vagas/new")
    public String novaVaga(Model model) {
        model.addAttribute("vaga", new Vaga());
        return "empresas/vagas/formVaga";
    }

    @PostMapping("/vagas/save")
    public String salvarVaga(@ModelAttribute("vaga") Vaga vaga, Authentication authentication) {
        String email = authentication.getName();
        Empresa empresa = empresaService.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada para o e-mail: " + email));

        vaga.setEmpresa(empresa);
        vagaService.createVaga(vaga);
        return "redirect:/empresa/vagas"; // rota correta conforme seu controller
    }

    @GetMapping("/vagas")
    public String listarVagas(Model model, Authentication authentication) {
        String email = authentication.getName(); // pega o e-mail do usuário autenticado
        Empresa empresa = empresaService.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada para o e-mail: " + email)); // busca a empresa no banco
        model.addAttribute("vagas", vagaService.getAllVagasByEmpresaId(empresa.getId()));
        return "empresas/vagas/listVagas";
    }
}

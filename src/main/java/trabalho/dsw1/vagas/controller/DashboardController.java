package trabalho.dsw1.vagas.controller;

import org.springframework.web.bind.annotation.GetMapping;

import trabalho.dsw1.vagas.domain.Profissional;
import trabalho.dsw1.vagas.domain.Vaga;

import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import trabalho.dsw1.vagas.service.impl.ProfissionalService;
import trabalho.dsw1.vagas.service.impl.VagaService;
import org.springframework.beans.factory.annotation.Autowired;
import java.security.Principal;


@Controller
public class DashboardController {

    @Autowired
    private ProfissionalService profissionalService;

    @Autowired
    private VagaService vagaService;

    @GetMapping("/dashboard")
    public String dashboardRedirect(Authentication authentication, Model model, Principal principal) {
    if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
        return "admin/dashboard-admin";
    } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_EMPRESA"))) {
        return "empresas/dashboard-empresas";
    } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_PROFISSIONAL"))) {
        Profissional profissional = profissionalService.findByEmail(principal.getName());

        List<Vaga> vagasDisponiveis = vagaService.buscarVagasNaoCandidatadas(profissional);
        List<Vaga> vagasInscritas = vagaService.buscarVagasCandidatadas(profissional);
         

        model.addAttribute("vagasDisponiveis", vagasDisponiveis);
        model.addAttribute("vagasInscritas", vagasInscritas);



        return "/profissionais/dashboard-profissional";
    }
    return "redirect:/"; // fallback
}    
}    
    


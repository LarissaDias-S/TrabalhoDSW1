package trabalho.dsw1.vagas.controller;

import org.springframework.web.bind.annotation.GetMapping;

import trabalho.dsw1.vagas.domain.Candidato;
import trabalho.dsw1.vagas.domain.Profissional;
import trabalho.dsw1.vagas.domain.Vaga;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import trabalho.dsw1.vagas.service.impl.ProfissionalService;
import trabalho.dsw1.vagas.service.impl.VagaService;
import org.springframework.beans.factory.annotation.Autowired;
import java.security.Principal;

import trabalho.dsw1.vagas.service.impl.CandidatoService;


@Controller
public class DashboardController {

    @Autowired
    private ProfissionalService profissionalService;

    @Autowired
    private VagaService vagaService;

    @Autowired
    private CandidatoService candidatoService;

    @GetMapping("/dashboard")
    public String dashboardRedirect(Authentication authentication, Model model, Principal principal) {
    if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
        return "admin/dashboard-admin";
    } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_EMPRESA"))) {
        return "empresas/dashboard-empresas";
    } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_PROFISSIONAL"))) {
        Profissional profissional = profissionalService.findByEmail(principal.getName());

        List<Vaga> vagasDisponiveis = vagaService.buscarVagasNaoCandidatadasSemData(profissional);
        
        List<Candidato> candidaturas = candidatoService.buscarCandidaturasPorProfissional(profissional);
        Map<Vaga, String> vagaStatusMap = new HashMap<>();
        for (Candidato c : candidaturas) {
            vagaStatusMap.put(c.getVaga(), c.getStatus());
        }

        model.addAttribute("vagasDisponiveis", vagasDisponiveis);
        model.addAttribute("vagasInscritas", vagaStatusMap);

        return "profissionais/dashboard-profissional";
    }
    return "redirect:/"; // fallback
}    
}    
    


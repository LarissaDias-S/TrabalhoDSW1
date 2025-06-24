package trabalho.dsw1.vagas.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import trabalho.dsw1.vagas.domain.Profissional;
import trabalho.dsw1.vagas.domain.Vaga;
import trabalho.dsw1.vagas.service.impl.VagaService;
import trabalho.dsw1.vagas.service.impl.ProfissionalService;

@Controller
public class VagasController {

    @Autowired
    private final VagaService vagaService;

    @Autowired
    private final ProfissionalService profissionalService;

    public VagasController(VagaService vagaService, 
                           ProfissionalService profissionalService) {
        this.vagaService = vagaService;
        this.profissionalService = profissionalService;
    }

    @GetMapping("/vagas")
    public String listarVagasPublicamente(Model model, Authentication auth, @RequestParam(required = false) String cidade) {

        List<Vaga> vagas;
        LocalDate hoje = LocalDate.now();
        
        vagas = vagaService.buscarVagasValidas(hoje);        
        
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_PROFISSIONAL"))) {
            Profissional profissional = profissionalService.findByEmail(auth.getName());
            vagas = vagaService.buscarVagasNaoCandidatadas(profissional, hoje);
        } 
        
        if (cidade == null || cidade.isBlank()) {
            if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_PROFISSIONAL"))) {
                Profissional profissional = profissionalService.findByEmail(auth.getName());
                vagas = vagaService.buscarVagasNaoCandidatadas(profissional, hoje);
            } else {
                vagas = vagaService.buscarVagasValidas(hoje);
            }
            model.addAttribute("cidade", "");
            //vagas = vagaService.buscarVagasValidas(hoje);
        } else {
            if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_PROFISSIONAL"))) {
                Profissional profissional = profissionalService.findByEmail(auth.getName());
                vagas = vagaService.findVagasAbertasPorCidade(cidade, profissional, hoje);
            } else {
                vagas = vagaService.findVagasAbertasPorCidadeSemLogar(cidade, hoje);
            }
            model.addAttribute("cidade", cidade); // pra manter o valor no campo
        }

        model.addAttribute("vagasDisponiveis", vagas);

        return "vagas/listagem";
    }
    
}

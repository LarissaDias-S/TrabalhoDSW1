package trabalho.dsw1.vagas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String listarVagasPublicamente(Model model, Authentication auth) {

        List<Vaga> vagas;
        
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_PROFISSIONAL"))) {
            Profissional profissional = profissionalService.findByEmail(auth.getName());
            vagas = vagaService.buscarVagasNaoCandidatadas(profissional);
        } else {
            vagas = vagaService.getAllVagas(); // ou outro método para listar todas as vagas para usuários não logados ou empresas
        }

        model.addAttribute("vagasDisponiveis", vagas);
        return "vagas";
    }
    
}

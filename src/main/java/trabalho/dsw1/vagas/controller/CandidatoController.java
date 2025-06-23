package trabalho.dsw1.vagas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.io.IOException;

import trabalho.dsw1.vagas.domain.Candidato;
import trabalho.dsw1.vagas.domain.Profissional;
import trabalho.dsw1.vagas.domain.Vaga;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import trabalho.dsw1.vagas.service.impl.ProfissionalService;
import trabalho.dsw1.vagas.service.impl.VagaService;

@Controller
public class CandidatoController {

    @Autowired
    private VagaService vagaService;

    @Autowired
    private ProfissionalService profissionalService;

    @Autowired
    private trabalho.dsw1.vagas.service.impl.CandidatoService candidatoService;

    @GetMapping("/profissionais/dashboard-profissional")
    public String dashboardProfissional(Model model, Authentication authentication) {
        String email = authentication.getName();
        Profissional profissional = profissionalService.findByEmail(email);

        List<Candidato> candidaturas = candidatoService.buscarCandidaturasPorProfissional(profissional);

        model.addAttribute("candidaturas", candidaturas);

        return "profissionais/dashboard-profissional";
    }

    @PostMapping("/candidatar")
    public String candidatar(@RequestParam Long vagaId,
                            @RequestParam MultipartFile curriculo,
                            Authentication authentication) throws IOException {
        String email = authentication.getName();
        Profissional profissional = profissionalService.findByEmail(email);
        if (profissional == null) {
            throw new IllegalArgumentException("Profissional não encontrado");
        }

        Vaga vaga = vagaService.findById(vagaId);
        if (vaga == null) {
            throw new IllegalArgumentException("Vaga não encontrada");
        }

        if (candidatoService.jaCandidatado(profissional, vaga)) {
            return "redirect:/profissionais/dashboard-profissional?erro=ja-inscrito";
        }

        Candidato candidato = new Candidato();
        candidato.setProfissional(profissional);
        candidato.setVaga(vaga);
        candidato.setCurriculo(curriculo.getBytes());

        candidatoService.save(candidato);

        return "index";
}

    
}

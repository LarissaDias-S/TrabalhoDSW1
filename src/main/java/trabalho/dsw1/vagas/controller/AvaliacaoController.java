package trabalho.dsw1.vagas.controller;

import trabalho.dsw1.vagas.model.Candidatura;
import trabalho.dsw1.vagas.repository.CandidaturaRepository;
import trabalho.dsw1.vagas.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class AvaliacaoController {

    @Autowired
    private CandidaturaRepository candidaturaRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping("/vaga/{id}/avaliar")
    public String listarCandidatos(@PathVariable Long id, Model model) {
        List<Candidatura> candidaturas = candidaturaRepository.findByVagaId(id);
        model.addAttribute("candidaturas", candidaturas);
        return "avaliacao";
    }

    @PostMapping("/candidatura/{id}/avaliar")
    public String avaliar(@PathVariable Long id,
                          @RequestParam String status,
                          @RequestParam(required = false) String link,
                          @RequestParam(required = false) String dataHora) {

        Candidatura candidatura = candidaturaRepository.findById(id).orElseThrow();

        candidatura.setStatus(status);

        if (status.equals("ENTREVISTA")) {
            candidatura.setLinkEntrevista(link);
            candidatura.setDataHoraEntrevista(LocalDateTime.parse(dataHora));
        }

        candidaturaRepository.save(candidatura);
        emailService.enviarResultado(candidatura);

        return "redirect:/vaga/" + candidatura.getVaga().getId() + "/avaliar";
    }
    @GetMapping("/candidatura/{id}/avaliar")
    public String exibirFormularioAvaliacao(@PathVariable Long id, Model model) {
        Candidatura candidatura = candidaturaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Candidatura não encontrada"));

        model.addAttribute("candidatura", candidatura);
        return "avaliar-candidatura";
    }

}

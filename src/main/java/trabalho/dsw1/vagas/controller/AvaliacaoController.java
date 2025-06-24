package trabalho.dsw1.vagas.controller;

import trabalho.dsw1.vagas.domain.Candidato;
import trabalho.dsw1.vagas.domain.Empresa;
import trabalho.dsw1.vagas.domain.Vaga;
import trabalho.dsw1.vagas.dao.ICandidatoDAO;
import trabalho.dsw1.vagas.dao.IVagaDAO;
import trabalho.dsw1.vagas.repository.EmpresaRepository;
import trabalho.dsw1.vagas.service.impl.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class AvaliacaoController {

    @Autowired
    private ICandidatoDAO candidaturaRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping("/vaga/{id}/avaliar")
    public String listarCandidatos(@PathVariable Long id, Model model) {
        List<Candidato> candidaturas = candidaturaRepository.findByVagaId(id);
        model.addAttribute("candidaturas", candidaturas);
        return "empresas/avaliacao";
    }

    @PostMapping("/candidatura/{id}/avaliar")
    public String avaliar(@PathVariable Long id,
                          @RequestParam String status,
                          @RequestParam(required = false) String link,
                          @RequestParam(required = false) String dataHora) {

        Candidato candidatura = candidaturaRepository.findById(id).orElseThrow();

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
        Candidato candidatura = candidaturaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Candidatura não encontrada"));

        model.addAttribute("candidatura", candidatura);
        return "empresas/avaliar-candidatura";
    }
    
    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private IVagaDAO vagaDAO;


    @GetMapping("/lista-vagas-empresa")
    public String listarVagasDaEmpresa(Model model, @AuthenticationPrincipal UserDetails userDetails) {
    String emailEmpresa = userDetails.getUsername(); // pega o email da empresa logada

    // buscar a empresa pelo e-mail
    Empresa empresa = empresaRepository.findByEmail(emailEmpresa).orElseThrow(() -> 
    new RuntimeException("Empresa não encontrada"));

    // busca as vagas da empresa
    List<Vaga> vagas = vagaDAO.findByEmpresaId(empresa.getId());

    model.addAttribute("vagas", vagas);
    return "empresas/lista-vagas-empresa";
    }

}

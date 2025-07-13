package trabalho.dsw1.vagas.controller;

import trabalho.dsw1.vagas.domain.Candidato;
import trabalho.dsw1.vagas.domain.Empresa;
import trabalho.dsw1.vagas.domain.Vaga;
import trabalho.dsw1.vagas.dao.ICandidatoDAO;
import trabalho.dsw1.vagas.dao.IVagaDAO;
import trabalho.dsw1.vagas.repository.EmpresaRepository;
import trabalho.dsw1.vagas.service.impl.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.http.HttpHeaders;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Controller
public class AvaliacaoController {

    @Autowired
    private ICandidatoDAO candidaturaRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private IVagaDAO vagaDAO;

    //lista de candidaturas para uma vaga {id} em especifico
    @GetMapping("/vagas/{id}/avaliacao")
    public String listarCandidatos(@PathVariable Long id, Model model) {
        List<Candidato> candidaturas = candidaturaRepository.findByVagaId(id);
        model.addAttribute("candidaturas", candidaturas);
        return "empresas/avaliacao";
    }

    //processo de avaliacao de uma candidatura
    @PostMapping("/vagas/{id}/avaliar")
    public String avaliar(@PathVariable Long id,
                          @RequestParam String status,
                          @RequestParam(required = false) String link,
                          @RequestParam(required = false) String dataHora) {

        Candidato candidatura = candidaturaRepository.findById(id).orElseThrow();

        candidatura.setStatus(status);
        
        if (status.equals("ENTREVISTA")) {
            candidatura.setLinkEntrevista(link);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            candidatura.setDataHoraEntrevista(LocalDateTime.parse(dataHora, formatter));
        }

        candidaturaRepository.save(candidatura);
        emailService.enviarResultado(candidatura);

        return "empresas/dashboard-empresas"; 
    }
    //candidatura a avaliar 
    @GetMapping("/vagas/{id}/avaliar")
    public String exibirFormularioAvaliacao(@PathVariable Long id, Model model) {
        List<Candidato> candidaturas = candidaturaRepository.findByVagaId(id);
        model.addAttribute("candidaturas", candidaturas);
        model.addAttribute("vagaId", id);
        return "empresas/avaliar-candidatura";
    }

    //listar vagas da empresa logada
    @GetMapping("/avaliacao")
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

    @GetMapping("/vagas/curriculo/{id}")
    public ResponseEntity<byte[]> viewCurriculo(@PathVariable Long id) {
        Candidato candidatura = candidaturaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Candidatura não encontrada"));

        byte[] pdf = candidatura.getCurriculo();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"curriculo.pdf\"");

        return ResponseEntity.ok()
            .headers(headers)
            .body(pdf);
    }
    

}

package trabalho.dsw1.vagas.controller;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import trabalho.dsw1.vagas.domain.Profissional;
import trabalho.dsw1.vagas.domain.Vaga;
import trabalho.dsw1.vagas.domain.Candidato;
import trabalho.dsw1.vagas.service.impl.ProfissionalService;
import trabalho.dsw1.vagas.service.impl.VagaService;
import trabalho.dsw1.vagas.service.impl.CandidatoService;

import java.util.List;
import java.security.Principal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

@Controller
@RequestMapping("/profissionais")
public class ProfissionalController {

    @Autowired
    private VagaService vagaService;
    private ProfissionalService profissionalService;
    private CandidatoService candidatoService;

    // Lista todos os profissionais
    @GetMapping
    public String listar(Model model) {
        List<Profissional> profissionais = profissionalService.findAll();
        model.addAttribute("profissionais", profissionais);
        return "profissionais/lista";  // nome da view Thymeleaf
    }

    // Formulário para criar novo profissional
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("profissional", new Profissional());
        return "profissionais/form";
    }

    // Salvar novo profissional ou atualizar existente
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Profissional profissional) {
        if (profissional.getId() == null) {
            profissionalService.save(profissional);
        }
        return "redirect:/profissionais";
    }

    // Formulário para editar profissional existente
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Profissional profissional = profissionalService.findById(id);
        if (profissional == null) {
            return "redirect:/profissionais";
        }
        model.addAttribute("profissional", profissional);
        return "profissionais/form";
    }

    // Deletar profissional
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        profissionalService.deleteById(id);
        return "redirect:/profissionais";
    }

    public String candidatar(
        @PathVariable Long id,
        @RequestParam("curriculo") MultipartFile arquivo,
        Principal principal,
        RedirectAttributes redirectAttributes) {

        // Buscar vaga
        Vaga vaga = vagaService.findById(id);
        if (vaga == null) {
            throw new IllegalArgumentException("Vaga não encontrada");
        }
        // Buscar profissional logado pelo e-mail do principal
        Profissional profissional = profissionalService.findByEmail(principal.getName());
        if (profissional == null) {
            throw new IllegalArgumentException("Profissional não encontrado");
        }

        if (arquivo.isEmpty() || !arquivo.getContentType().equals("application/pdf")) {
            redirectAttributes.addFlashAttribute("erro", "Por favor, envie um arquivo PDF válido.");
            return "/vagas/" + id;
        }

        // Salvar arquivo no servidor (exemplo simples, ajustar conforme necessário)
        String nomeArquivo = UUID.randomUUID() + ".pdf";
        Path caminho = Paths.get("uploads/" + nomeArquivo);
        try {
            Files.createDirectories(caminho.getParent());
            arquivo.transferTo(caminho.toFile());
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar o arquivo.");
            return "/vagas/" + id;
        }
        
        // Salvar candidatura
        try {
            // Supondo que exista um construtor Candidato(Profissional, Vaga, String)
            trabalho.dsw1.vagas.domain.Candidato candidato = new Candidato(profissional, vaga, nomeArquivo.getBytes());
            candidatoService.save(candidato);
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "/vagas/" + id;
        }

        redirectAttributes.addFlashAttribute("sucesso", "Candidatura realizada com sucesso!");
        return "/vagas/" + id;
    }
}

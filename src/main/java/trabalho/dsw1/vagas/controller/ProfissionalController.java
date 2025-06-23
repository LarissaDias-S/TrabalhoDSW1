package trabalho.dsw1.vagas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import trabalho.dsw1.vagas.domain.Profissional;
import trabalho.dsw1.vagas.service.spec.IProfissionalService;

import java.util.List;

@Controller
@RequestMapping("/profissionais")
public class ProfissionalController {

    @Autowired
    private IProfissionalService profissionalService;

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
}

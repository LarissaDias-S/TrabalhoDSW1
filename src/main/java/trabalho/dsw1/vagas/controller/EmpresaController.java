package trabalho.dsw1.vagas.controller;

import trabalho.dsw1.vagas.model.Empresa;
import trabalho.dsw1.vagas.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize; // For method-level security

@Controller
@RequestMapping("/admin/empresas") // All paths under here require ADMIN role due to SecurityConfig
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    // List all companies (Read)
    @GetMapping
    public String listEmpresas(Model model) {
        model.addAttribute("empresas", empresaService.findAllEmpresas());
        return "admin/empresas/list"; // Corresponds to /src/main/resources/templates/admin/empresas/list.html
    }

    // Show form to add new company (Create - display form)
    @GetMapping("/new")
    public String showNewEmpresaForm(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "admin/empresas/form"; // Corresponds to /src/main/resources/templates/admin/empresas/form.html
    }

    // Save new company (Create - submit form)
    @PostMapping("/save")
    public String saveEmpresa(@ModelAttribute("empresa") Empresa empresa) {
        empresaService.saveEmpresa(empresa);
        return "redirect:/admin/empresas"; // Redirect back to the list
    }

    // Show form to edit existing company (Update - display form)
    @GetMapping("/edit/{id}")
    public String showEditEmpresaForm(@PathVariable Long id, Model model) {
        empresaService.findEmpresaById(id).ifPresent(empresa -> model.addAttribute("empresa", empresa));
        return "admin/empresas/form";
    }

    // Delete company (Delete)
    @GetMapping("/delete/{id}")
    public String deleteEmpresa(@PathVariable Long id) {
        empresaService.deleteEmpresa(id);
        return "redirect:/admin/empresas";
    }
}
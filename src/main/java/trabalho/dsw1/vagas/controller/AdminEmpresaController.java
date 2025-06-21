package trabalho.dsw1.vagas.controller;

import trabalho.dsw1.vagas.model.Empresa;
import trabalho.dsw1.vagas.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize; 


@Controller
@RequestMapping("/admin/empresas") // Todos os caminhos abaixo precisam do role ADMIN, configurado em SecurityConfig
public class AdminEmpresaController {

    @Autowired
    private EmpresaService empresaService;

    // Listar todas as empresas
    @GetMapping
    public String listEmpresas(Model model) {
        model.addAttribute("empresas", empresaService.findAllEmpresas());
        return "admin/empresas/list";
    }

    // Mostrar formulario que adiciona novas empresas
    @GetMapping("/new")
    public String showNewEmpresaForm(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "admin/empresas/form";
    }

    // salvar nova empresa
    @PostMapping("/save")
    public String saveEmpresa(@ModelAttribute("empresa") Empresa empresa) {
        empresaService.saveEmpresa(empresa);
        return "redirect:/admin/empresas"; // Redirect back to the list
    }

    //mostrar formulario que edita empresa existente
    @GetMapping("/edit/{id}")
    public String showEditEmpresaForm(@PathVariable Long id, Model model) {
        empresaService.findEmpresaById(id).ifPresent(empresa -> model.addAttribute("empresa", empresa));
        return "admin/empresas/form";
    }

    // excluir empresa
    @GetMapping("/delete/{id}")
    public String deleteEmpresa(@PathVariable Long id) {
        empresaService.deleteEmpresa(id);
        return "redirect:/admin/empresas";
    }
    

}
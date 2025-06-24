package trabalho.dsw1.vagas.controller;

import trabalho.dsw1.vagas.domain.Empresa;
import trabalho.dsw1.vagas.domain.Profissional;
import trabalho.dsw1.vagas.service.EmpresaService;
import trabalho.dsw1.vagas.service.impl.ProfissionalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;


@Controller
@RequestMapping("/admin") // Todos os caminhos abaixo precisam do role ADMIN, configurado em SecurityConfig
public class AdminController {

    @GetMapping("/dashboard")
    public String dashboardAdmin() {
        return "admin/dashboard-admin"; // buscará por templates/admin/dashboard.html
    }
    @Autowired
    private ProfissionalService profissionalService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Listar todos os profissionais
    @GetMapping("/profissionais")
    public String listProfissionais(Model model) {
        model.addAttribute("profissionais", profissionalService.findAll());
        return "admin/profissionais/list";
    }

    // Mostrar formulário para novo profissional
    @GetMapping("/profissionais/new")
    public String showNewProfissionalForm(Model model) {
        model.addAttribute("profissional", new Profissional());
        return "admin/profissionais/form";
    }

    // Salvar profissional
    @PostMapping("/profissionais/save")
    public String saveProfissional(@ModelAttribute("profissional") Profissional profissional) {
        if (profissional.getId() != null) {
            Profissional existente = profissionalService.findById(profissional.getId());

            if (profissional.getPassword() == null || profissional.getPassword().isBlank()) {
                // Se senha está em branco, mantém a antiga
                profissional.setPassword(existente.getPassword());
            } else {
                profissional.setPassword(passwordEncoder.encode(profissional.getPassword()));
            }
        } else {
            // Novo cadastro — senha obrigatória
            profissional.setPassword(passwordEncoder.encode(profissional.getPassword()));
        }

        
        profissional.setRole("ROLE_PROFISSIONAL");
        profissional.setEnabled(true);
        profissionalService.save(profissional);

        return "redirect:/admin/profissionais";
    }

    // Mostrar formulário para editar
    @GetMapping("/profissionais/edit/{id}")
    public String showEditProfissionalForm(@PathVariable Long id, Model model) {
        Profissional profissional = profissionalService.findById(id);
        if (profissional != null) {
            model.addAttribute("profissional", profissional);
        }
        return "admin/profissionais/form";
    }

    // Excluir profissional
    @GetMapping("/profissionais/delete/{id}")
    public String deleteProfissional(@PathVariable Long id) {
        profissionalService.deleteById(id);
        return "redirect:/admin/profissionais";
    }

    @Autowired
    private EmpresaService empresaService;

    // Listar todas as empresas
    @GetMapping("/empresas")
    public String listEmpresas(Model model) {
        model.addAttribute("empresas", empresaService.findAllEmpresas());
        return "admin/empresas/list";
    }

    // Mostrar formulario que adiciona novas empresas
    @GetMapping("/empresas/new")
    public String showNewEmpresaForm(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "admin/empresas/form";
    }

    // salvar nova empresa
    @PostMapping("/empresas/save")
    public String saveEmpresa(@ModelAttribute("empresa") Empresa empresa) {
        if (empresa.getId() != null) {
            Empresa existente = empresaService.findEmpresaById(empresa.getId()).orElse(null);
            if (existente != null) {
                if (empresa.getSenha() == null || empresa.getSenha().isBlank()) {
                // manter a senha antiga
                empresa.setSenha(existente.getSenha());
            } else {
                empresa.setSenha(passwordEncoder.encode(empresa.getSenha()));
            }
        }
    } else {
        // nova empresa
        empresa.setSenha(passwordEncoder.encode(empresa.getSenha()));
    }
    empresaService.saveEmpresa(empresa);
    return "redirect:/admin/empresas";
    }



    //mostrar formulario que edita empresa existente
    @GetMapping("/empresas/edit/{id}")
    public String showEditEmpresaForm(@PathVariable Long id, Model model) {
        empresaService.findEmpresaById(id).ifPresent(empresa -> model.addAttribute("empresa", empresa));
        return "admin/empresas/form";
    }

    // excluir empresa
    @GetMapping("/empresas/delete/{id}")
    public String deleteEmpresa(@PathVariable Long id) {
        empresaService.deleteEmpresa(id);
        return "redirect:/admin/empresas";
    }
    

}
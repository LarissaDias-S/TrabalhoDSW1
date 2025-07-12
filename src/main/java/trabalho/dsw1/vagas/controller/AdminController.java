package trabalho.dsw1.vagas.controller;

import trabalho.dsw1.vagas.domain.Empresa;
import trabalho.dsw1.vagas.domain.Profissional;
import trabalho.dsw1.vagas.service.EmpresaService;
import trabalho.dsw1.vagas.service.impl.ProfissionalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError; // Para adicionar erros manualmente, se necessário
import jakarta.validation.Valid; // Ainda usaremos @Valid para os outros campos
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public String dashboardAdmin() {
        return "admin/dashboard-admin";
    }

    @Autowired
    private ProfissionalService profissionalService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // --- Métodos para Profissionais ---

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
    public String saveProfissional(
            @ModelAttribute("profissional") Profissional profissional, // SEM @Valid aqui inicialmente
            BindingResult result,
            Model model) {

        // 1. Captura a senha submetida pelo formulário (pode ser vazia na edição)
        String submittedPassword = profissional.getPassword();

        // 2. Temporariamente seta a senha como null no objeto para que @Valid não a valide agora.
        profissional.setPassword(null);

        // 3. Executa a validação @Valid para TODOS OS OUTROS CAMPOS do objeto.
        // O Spring automaticamente popula o 'result' com os erros dos campos validados.
        // Estamos usando um truque aqui para forçar a validação após o @ModelAttribute.
        // O @Valid no parâmetro do método é mais comum, mas com a senha, isso pode causar o loop de erros.
        // Re-injetar o objeto para ser validado novamente por @Valid.
        // Isso pode ser feito assim:
        org.springframework.validation.Validator validator = new org.springframework.validation.beanvalidation.LocalValidatorFactoryBean();
        validator.validate(profissional, result);


        // 4. Lógica de Validação Manual da Senha
        boolean isEditing = profissional.getId() != null;
        boolean passwordProvided = submittedPassword != null && !submittedPassword.isBlank();

        if (!isEditing) { // Regras para NOVO CADASTRO
            if (!passwordProvided) {
                // Senha é obrigatória para novo cadastro
                result.addError(new FieldError("profissional", "password", submittedPassword, false, new String[]{"profissional.senha.notblank"}, null, "A senha é obrigatória."));
            } else if (submittedPassword.length() < 6 || submittedPassword.length() > 64) {
                // Validação de tamanho para novo cadastro
                result.addError(new FieldError("profissional", "password", submittedPassword, false, new String[]{"profissional.senha.size"}, null, "A senha deve ter entre 6 e 64 caracteres."));
            }
            // Se a senha foi fornecida e atende ao tamanho, não adicionamos erros.
        } else { // Regras para EDIÇÃO
            if (passwordProvided) { // Se uma senha FOI fornecida na edição (o usuário digitou algo)
                if (submittedPassword.length() < 6 || submittedPassword.length() > 64) {
                    // Validação de tamanho para senha fornecida na edição
                    result.addError(new FieldError("profissional", "password", submittedPassword, false, new String[]{"profissional.senha.size"}, null, "A senha deve ter entre 6 e 64 caracteres."));
                }
            }
            // Se a senha NÃO foi fornecida na edição (campo em branco), NÃO adicionamos erros.
            // Isso permite que a senha existente seja mantida.
        }

        // 5. Verifica se há *quaisquer* erros de validação (dos outros campos ou da senha)
        if (result.hasErrors()) {
            // Reverter a senha para o objeto para que outros campos sejam exibidos corretamente.
            // Se a senha original era null/vazia, ela permanece null/vazia para não ser exibida.
            profissional.setPassword(submittedPassword);
            return "admin/profissionais/form"; // Retorna ao formulário para exibir os erros
        }

        // 6. Processamento da Senha (Codificação ou Manutenção da Antiga)
        if (isEditing) {
            if (passwordProvided) {
                // Nova senha fornecida na edição, codifica
                profissional.setPassword(passwordEncoder.encode(submittedPassword));
            } else {
                // Senha não fornecida na edição, mantém a antiga do banco de dados
                Profissional existente = profissionalService.findById(profissional.getId());
                if (existente != null) {
                    profissional.setPassword(existente.getPassword());
                } else {
                    // Situação de erro: ID de edição inválido
                    result.reject("profissional.notfound", "Profissional não encontrado para edição.");
                    return "admin/profissionais/form"; // Retorna ao formulário com o erro
                }
            }
        } else {
            // Novo cadastro, senha já validada e fornecida, apenas codifica
            profissional.setPassword(passwordEncoder.encode(submittedPassword));
        }

        // 7. Salva o profissional
        profissional.setRole("ROLE_PROFISSIONAL");
        profissional.setEnabled(true);
        profissionalService.save(profissional);

        return "redirect:/admin/profissionais"; // Redireciona para a lista de profissionais
    }

    // Mostrar formulário para editar
    @GetMapping("/profissionais/edit/{id}")
    public String showEditProfissionalForm(@PathVariable Long id, Model model) {
        Profissional profissional = profissionalService.findById(id);
        if (profissional != null) {
            model.addAttribute("profissional", profissional);
        } else {
            return "redirect:/admin/profissionais";
        }
        return "admin/profissionais/form";
    }

    // Excluir profissional
    @GetMapping("/profissionais/delete/{id}")
    public String deleteProfissional(@PathVariable Long id) {
        profissionalService.deleteById(id);
        return "redirect:/admin/profissionais";
    }

    // --- Métodos para Empresas ---

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

    // Salvar nova empresa
    @PostMapping("/empresas/save")
    public String saveEmpresa(
            @ModelAttribute("empresa") Empresa empresa, // SEM @Valid aqui inicialmente
            BindingResult result,
            Model model) {

        String submittedSenha = empresa.getSenha();
        empresa.setSenha(null); // Temporariamente define como null para não validar a senha aqui com @Valid

        // Executa a validação @Valid para os outros campos
        org.springframework.validation.Validator validator = new org.springframework.validation.beanvalidation.LocalValidatorFactoryBean();
        validator.validate(empresa, result);

        // Lógica de Validação Manual da Senha da Empresa
        boolean isEditing = empresa.getId() != null;
        boolean senhaProvided = submittedSenha != null && !submittedSenha.isBlank();

        if (!isEditing) { // Novo Cadastro
            if (!senhaProvided) {
                result.addError(new FieldError("empresa", "senha", submittedSenha, false, new String[]{"empresa.senha.notblank"}, null, "A senha é obrigatória."));
            } else if (submittedSenha.length() < 6 || submittedSenha.length() > 64) {
                result.addError(new FieldError("empresa", "senha", submittedSenha, false, new String[]{"empresa.senha.size"}, null, "A senha deve ter entre 6 e 64 caracteres."));
            }
        } else { // Edição
            if (senhaProvided) {
                if (submittedSenha.length() < 6 || submittedSenha.length() > 64) {
                    result.addError(new FieldError("empresa", "senha", submittedSenha, false, new String[]{"empresa.senha.size"}, null, "A senha deve ter entre 6 e 64 caracteres."));
                }
            }
        }

        // Verifica se há erros de validação
        if (result.hasErrors()) {
            empresa.setSenha(submittedSenha); // Reverter a senha para o objeto
            return "admin/empresas/form";
        }

        // Processamento da Senha da Empresa
        if (isEditing) {
            if (senhaProvided) {
                empresa.setSenha(passwordEncoder.encode(submittedSenha));
            } else {
                Empresa existente = empresaService.findEmpresaById(empresa.getId()).orElse(null);
                if (existente != null) {
                    empresa.setSenha(existente.getSenha());
                } else {
                    result.reject("empresa.notfound", "Empresa não encontrada para edição.");
                    return "admin/empresas/form";
                }
            }
        } else {
            empresa.setSenha(passwordEncoder.encode(submittedSenha));
        }

        empresaService.saveEmpresa(empresa);
        return "redirect:/admin/empresas";
    }

    // Mostrar formulario que edita empresa existente
    @GetMapping("/empresas/edit/{id}")
    public String showEditEmpresaForm(@PathVariable Long id, Model model) {
        empresaService.findEmpresaById(id).ifPresent(empresa -> model.addAttribute("empresa", empresa));
        return "admin/empresas/form";
    }

    // Excluir empresa
    @GetMapping("/empresas/delete/{id}")
    public String deleteEmpresa(@PathVariable Long id) {
        empresaService.deleteEmpresa(id);
        return "redirect:/admin/empresas";
    }
}
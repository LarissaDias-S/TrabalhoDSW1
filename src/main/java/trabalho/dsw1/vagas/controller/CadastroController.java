package trabalho.dsw1.vagas.controller;

import jakarta.validation.Valid; // Importe esta anotação

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource; // Importe MessageSource
import org.springframework.context.i18n.LocaleContextHolder; // Importe LocaleContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Importe Model
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError; // Importe ObjectError
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import trabalho.dsw1.vagas.domain.Empresa;
import trabalho.dsw1.vagas.domain.Profissional;
import trabalho.dsw1.vagas.repository.EmpresaRepository;
import trabalho.dsw1.vagas.dao.IProfissionalDAO;
import trabalho.dsw1.vagas.service.impl.ProfissionalService;

import java.util.stream.Collectors; // Importe Collectors

@Controller
public class CadastroController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private ProfissionalService profissionalService;

    @Autowired
    private IProfissionalDAO profissionalRepository;

    @Autowired
    private MessageSource messageSource; // Injetar MessageSource para buscar mensagens do ValidationMessages.properties

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        if (!model.containsAttribute("profissional")) {
            model.addAttribute("profissional", new Profissional());
        }
        if (!model.containsAttribute("empresa")) {
            model.addAttribute("empresa", new Empresa());
        }
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String processarCadastro(@RequestParam String tipo,
                                    @Valid @ModelAttribute("profissional") Profissional profissional,
                                    BindingResult profissionalBindingResult,
                                    @Valid @ModelAttribute("empresa") Empresa empresa,
                                    BindingResult empresaBindingResult,
                                    RedirectAttributes ra,
                                    Model model) {

        if ("empresa".equals(tipo)) {
            // A validação @Valid já preencheu empresaBindingResult
            // Verificações de unicidade adicionais
            if (empresaRepository.findByEmail(empresa.getEmail()) != null) {
                empresaBindingResult.rejectValue("email", "error.empresa.email"); // Usar apenas a chave
            }
            if (empresaRepository.findByCnpj(empresa.getCnpj()) != null) {
                empresaBindingResult.rejectValue("cnpj", "error.empresa.cnpj"); // Usar apenas a chave
            }

            if (empresaBindingResult.hasErrors()) {
                model.addAttribute("tipoFormAtivo", "empresa");
                // Coleta todas as mensagens de erro do BindingResult
                String errorMessage = empresaBindingResult.getAllErrors().stream()
                        .map(error -> {
                            // Tenta resolver a mensagem usando MessageSource, se for uma chave.
                            // Caso contrário, usa a mensagem padrão do erro.
                            String message = error.getDefaultMessage();
                            if (message != null && message.startsWith("{") && message.endsWith("}")) {
                                try {
                                    // Remove as chaves {} e tenta resolver
                                    message = messageSource.getMessage(message.substring(1, message.length() - 1), null, LocaleContextHolder.getLocale());
                                } catch (Exception e) {
                                    // Se a chave não for encontrada, usa a mensagem original
                                }
                            } else if (error.getCode() != null) {
                                // Tenta resolver a mensagem usando o código do erro (para rejectValue)
                                try {
                                    message = messageSource.getMessage(error.getCode(), error.getArguments(), LocaleContextHolder.getLocale());
                                } catch (Exception e) {
                                    // Se o código não for encontrado, usa a mensagem padrão
                                }
                            }
                            return message;
                        })
                        .collect(Collectors.joining("; ")); // Junta todas as mensagens com um ponto e vírgula
                model.addAttribute("error", errorMessage); // Adiciona a mensagem combinada ao modelo
                return "cadastro";
            }

            empresa.setSenha(encoder.encode(empresa.getSenha()));
            empresaRepository.save(empresa);
            ra.addFlashAttribute("success", messageSource.getMessage("cadastro.sucesso", null, LocaleContextHolder.getLocale()));
            return "redirect:/cadastro-sucesso";

        } else if ("profissional".equals(tipo)) {
            // A validação @Valid já preencheu profissionalBindingResult
            // Verificação de unicidade de email para Profissional
            if (profissionalRepository.findByEmail(profissional.getEmail()) != null) {
                profissionalBindingResult.rejectValue("email", "error.profissional.email"); // Usar apenas a chave
            }

            if (profissionalBindingResult.hasErrors()) {
                model.addAttribute("tipoFormAtivo", "profissional");
                // Coleta todas as mensagens de erro do BindingResult
                String errorMessage = profissionalBindingResult.getAllErrors().stream()
                        .map(error -> {
                            String message = error.getDefaultMessage();
                            if (message != null && message.startsWith("{") && message.endsWith("}")) {
                                try {
                                    message = messageSource.getMessage(message.substring(1, message.length() - 1), null, LocaleContextHolder.getLocale());
                                } catch (Exception e) {
                                }
                            } else if (error.getCode() != null) {
                                try {
                                    message = messageSource.getMessage(error.getCode(), error.getArguments(), LocaleContextHolder.getLocale());
                                } catch (Exception e) {
                                }
                            }
                            return message;
                        })
                        .collect(Collectors.joining("; "));
                model.addAttribute("error", errorMessage);
                return "cadastro";
            }

            profissional.setPassword(encoder.encode(profissional.getPassword()));
            if (profissional.getRole() == null || profissional.getRole().isEmpty()) {
                 profissional.setRole("ROLE_PROFISSIONAL");
            }
            profissional.setEnabled(true);
            profissionalService.save(profissional);
            ra.addFlashAttribute("success", messageSource.getMessage("cadastro.sucesso", null, LocaleContextHolder.getLocale()));
            return "redirect:/cadastro-sucesso";
        }

        ra.addFlashAttribute("error", messageSource.getMessage("cadastro.tipoInvalido", null, LocaleContextHolder.getLocale()));
        return "redirect:/cadastro";
    }

    @GetMapping("/cadastro-sucesso")
    public String cadastroSucesso() {
        return "cadastro-sucesso";
    }
}
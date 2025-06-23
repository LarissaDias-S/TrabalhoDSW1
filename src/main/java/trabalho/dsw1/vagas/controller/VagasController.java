package trabalho.dsw1.vagas.controller;

<<<<<<< HEAD
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
=======
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> 3bf467c (R8 e mudancas no R6)
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
<<<<<<< HEAD
import org.springframework.ui.Model;

import trabalho.dsw1.vagas.domain.Profissional;
import trabalho.dsw1.vagas.domain.Vaga;
import trabalho.dsw1.vagas.service.impl.VagaService;
import trabalho.dsw1.vagas.service.impl.ProfissionalService;
=======
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import trabalho.dsw1.vagas.model.Vaga;
import trabalho.dsw1.vagas.model.Empresa;
import trabalho.dsw1.vagas.repository.VagaRepository;
import trabalho.dsw1.vagas.repository.EmpresaRepository;


import java.time.LocalDate;
import java.util.List;

>>>>>>> 3bf467c (R8 e mudancas no R6)

@Controller
public class VagasController {

    @Autowired
<<<<<<< HEAD
    private final VagaService vagaService;

    @Autowired
    private final ProfissionalService profissionalService;

    public VagasController(VagaService vagaService, 
                           ProfissionalService profissionalService) {
        this.vagaService = vagaService;
        this.profissionalService = profissionalService;
    }

    @GetMapping("/vagas")
    public String listarVagasPublicamente(Model model, Authentication auth) {

        List<Vaga> vagas;
        
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_PROFISSIONAL"))) {
            Profissional profissional = profissionalService.findByEmail(auth.getName());
            vagas = vagaService.buscarVagasNaoCandidatadas(profissional);
        } else {
            vagas = vagaService.getAllVagas(); // ou outro método para listar todas as vagas para usuários não logados ou empresas
        }

        model.addAttribute("vagasDisponiveis", vagas);
        return "vagas";
    }
=======
    private VagaRepository vagaRepository;
>>>>>>> 3bf467c (R8 e mudancas no R6)
    
    @Autowired
    private EmpresaRepository empresaRepository;


    @GetMapping("/vagas")
    public String listarVagas(@RequestParam(required = false) String cidade, Model model) {
        LocalDate hoje = LocalDate.now();
        List<Vaga> vagas;

        if (cidade == null || cidade.isBlank()) {
            vagas = vagaRepository.findVagasAbertas(hoje);
        } else {
            vagas = vagaRepository.findVagasAbertasPorCidade(hoje, cidade);
            model.addAttribute("cidade", cidade); // pra manter o valor no campo
        }

        model.addAttribute("vagas", vagas);
        return "vagas/listagem";
    }
    //lista de vagas da empresa
    @GetMapping("/vaga")
    public String listarVagasDaEmpresa(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        Empresa empresa = empresaRepository.findByEmail(email)
        	    .orElseThrow(() -> new RuntimeException("Empresa não encontrada com email: " + email));

        List<Vaga> vagas = vagaRepository.findByEmpresa(empresa);
        model.addAttribute("vagas", vagas);
        return "lista-vagas-empresa";
    }

}

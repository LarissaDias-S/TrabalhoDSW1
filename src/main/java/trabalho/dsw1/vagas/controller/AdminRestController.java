package trabalho.dsw1.vagas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import trabalho.dsw1.vagas.domain.Empresa;
import trabalho.dsw1.vagas.domain.Profissional;
import trabalho.dsw1.vagas.domain.Vaga;
import trabalho.dsw1.vagas.service.EmpresaService;
import trabalho.dsw1.vagas.service.impl.ProfissionalService;
import trabalho.dsw1.vagas.service.impl.VagaService;

@RestController
@RequestMapping("/api")
public class AdminRestController {

    @Autowired
    private ProfissionalService profissionalService;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private VagaService vagaService;

    // ---------------- PROFISSIONAIS ----------------

    // CREATE
    @PostMapping("/profissionais")
    public ResponseEntity<Profissional> criarProfissional(@RequestBody Profissional profissional) {
        Profissional salvo = profissionalService.save(profissional);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // READ ALL
    @GetMapping("/profissionais")
    public ResponseEntity<List<Profissional>> listarProfissionais() {
        List<Profissional> lista = profissionalService.findAll();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    // READ BY ID
    @GetMapping("/profissionais/{id}")
    public ResponseEntity<Profissional> buscarProfissionalPorId(@PathVariable Long id) {
        Profissional profissional = profissionalService.findById(id);
        if (profissional == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(profissional);
    }

    // UPDATE
    @PutMapping("/profissionais/{id}")
    public ResponseEntity<Profissional> atualizarProfissional(@PathVariable Long id, @RequestBody Profissional profissional) {
        Profissional existente = profissionalService.findById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        profissional.setId(id);
        Profissional atualizado = profissionalService.save(profissional);
        return ResponseEntity.ok(atualizado);
    }

    // DELETE
    @DeleteMapping("/profissionais/{id}")
    public ResponseEntity<Void> removerProfissional(@PathVariable Long id) {
        Profissional existente = profissionalService.findById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        profissionalService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ---------------- EMPRESAS ----------------

    // CREATE
    @PostMapping("/empresas")
    public ResponseEntity<Empresa> criarEmpresa(@RequestBody Empresa empresa) {
        Empresa salva = empresaService.saveEmpresa(empresa);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    // READ ALL
    @GetMapping("/empresas")
    public ResponseEntity<List<Empresa>> listarEmpresas() {
        List<Empresa> lista = empresaService.findAllEmpresas();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    // READ BY ID
    @GetMapping("/empresas/{id}")
    public ResponseEntity<Empresa> buscarEmpresaPorId(@PathVariable Long id) {
        Optional<Empresa> empresa = empresaService.findEmpresaById(id);
        return empresa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // READ BY CIDADE
    @GetMapping("/empresas/cidades/{nome}")
    public ResponseEntity<List<Empresa>> listarEmpresasPorCidade(@PathVariable String nome) {
        List<Empresa> lista = empresaService.findEmpresasByCidade(nome);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    // UPDATE
    @PutMapping("/empresas/{id}")
    public ResponseEntity<Empresa> atualizarEmpresa(@PathVariable Long id, @RequestBody Empresa empresa) {
        Optional<Empresa> existente = empresaService.findEmpresaById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        empresa.setId(id);
        Empresa atualizada = empresaService.saveEmpresa(empresa);
        return ResponseEntity.ok(atualizada);
    }

    // DELETE
    @DeleteMapping("/empresas/{id}")
    public ResponseEntity<Void> removerEmpresa(@PathVariable Long id) {
        Optional<Empresa> existente = empresaService.findEmpresaById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        empresaService.deleteEmpresa(id);
        return ResponseEntity.noContent().build();
    }

    // ---------------- VAGAS ----------------

    // READ ALL
    @GetMapping("/vagas")
    public ResponseEntity<List<Vaga>> listarVagas() {
        List<Vaga> lista = vagaService.getAllVagas();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    // READ BY ID
    @GetMapping("/vagas/{id}")
    public ResponseEntity<Vaga> buscarVagaPorId(@PathVariable Long id) {
        Vaga vaga = vagaService.findById(id);
        if (vaga == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(vaga);
    }

    // READ VAGAS EM ABERTO DA EMPRESA
    @GetMapping("/vagas/empresas/{id}")
    public ResponseEntity<List<Vaga>> listarVagasEmAbertoPorEmpresa(@PathVariable Long id) {
        List<Vaga> lista = vagaService.getAllVagasByEmpresaId(id);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }
}

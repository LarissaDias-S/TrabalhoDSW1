package trabalho.dsw1.vagas.service;

import trabalho.dsw1.vagas.domain.Empresa;
import trabalho.dsw1.vagas.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    public List<Empresa> findAllEmpresas() {
        return empresaRepository.findAll();
    }

    public Optional<Empresa> findEmpresaById(Long id) {
        return empresaRepository.findById(id);
    }

    public Optional<Empresa> findByEmail(String email) {
        return empresaRepository.findByEmail(email);
    }

    public Empresa saveEmpresa(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public void deleteEmpresa(Long id) {
        empresaRepository.deleteById(id);
    }
}
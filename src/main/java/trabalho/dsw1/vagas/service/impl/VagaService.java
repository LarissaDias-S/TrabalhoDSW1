package trabalho.dsw1.vagas.service.impl;

import trabalho.dsw1.vagas.domain.Profissional;
import trabalho.dsw1.vagas.domain.Vaga;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import trabalho.dsw1.vagas.dao.IVagaDAO;

@Service
public class VagaService {

    @Autowired
    private IVagaDAO vagaDAO; 
    
    public Vaga createVaga(Vaga vaga) {
        return vagaDAO.save(vaga);
    }
    
    public List<Vaga> getAllVagas() {
        return vagaDAO.findAll();
    }
    
    public void deleteVaga(Long id) {
        vagaDAO.deleteById(id);
    }

    public Vaga findById(Long id) {
        return vagaDAO.findById(id).orElse(null);
    }

    public List<Vaga> getAllVagasByEmpresaId(Long empresaId) {
        return vagaDAO.findByEmpresaId(empresaId);
    }

    public List<Vaga> buscarVagasNaoCandidatadas(Profissional profissional) {
        return vagaDAO.findVagasNaoCandidatadas(profissional.getId());
    }

    public List<Vaga> buscarVagasCandidatadas(Profissional profissional) {
        return vagaDAO.findVagasCandidatadas(profissional.getId());
    }
    
}

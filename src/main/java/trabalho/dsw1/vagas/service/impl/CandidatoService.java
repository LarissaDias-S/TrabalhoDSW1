package trabalho.dsw1.vagas.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import trabalho.dsw1.vagas.dao.ICandidatoDAO;
import trabalho.dsw1.vagas.domain.Candidato;
import trabalho.dsw1.vagas.domain.Profissional;
import trabalho.dsw1.vagas.domain.Vaga;

@Service
public class CandidatoService {
    
    @Autowired
    private ICandidatoDAO candidatoDAO;

    public Candidato save(Candidato candidato) {
        return candidatoDAO.save(candidato);
    }

    public List<Vaga> buscarVagasCandidatadas(Profissional profissional) {
        return candidatoDAO.findVagasByProfissional(profissional);
    }

    public boolean jaCandidatado(Profissional profissional, Vaga vaga) {
    return candidatoDAO.existsByProfissionalAndVaga(profissional, vaga);
}
}


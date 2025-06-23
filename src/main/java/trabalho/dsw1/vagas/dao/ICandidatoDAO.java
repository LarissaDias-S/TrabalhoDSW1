package trabalho.dsw1.vagas.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import trabalho.dsw1.vagas.domain.Candidato;
import trabalho.dsw1.vagas.domain.Profissional;
import trabalho.dsw1.vagas.domain.Vaga;

@SuppressWarnings("unchecked")
public interface ICandidatoDAO extends CrudRepository<Candidato, Long> {

    Candidato save(Candidato candidato);

    void deleteById(Long id);

    List<Candidato> findByProfissional(Profissional profissional);
        
    @Query("SELECT COUNT(c) > 0 FROM Candidato c WHERE c.profissional = :profissional AND c.vaga = :vaga")
    boolean existsByProfissionalAndVaga(Profissional profissional, Vaga vaga);
    
}

package trabalho.dsw1.vagas.dao;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import trabalho.dsw1.vagas.domain.Vaga;

@SuppressWarnings("unchecked")
public interface IVagaDAO extends CrudRepository<Vaga, Long>  {
    Vaga save(Vaga Vaga);

    void deleteById(Long id);

    Optional<Vaga> findById(Long id);
    
    List<Vaga> findAll();

    List<Vaga> findByEmpresaId(Long empresaId);

    @Query("SELECT v FROM Vaga v WHERE v.id NOT IN (SELECT c.vaga.id FROM Candidato c WHERE c.profissional.id = :profissionalId)")
    List<Vaga> findVagasNaoCandidatadas(Long profissionalId);

    @Query("SELECT v FROM Vaga v WHERE v.id IN (SELECT c.vaga.id FROM Candidato c WHERE c.profissional.id = :profissionalId)")
    List<Vaga> findVagasCandidatadas(Long profissionalId);
}

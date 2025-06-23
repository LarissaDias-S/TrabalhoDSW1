package trabalho.dsw1.vagas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trabalho.dsw1.vagas.model.Candidatura;
import java.util.List;

public interface CandidaturaRepository extends JpaRepository<Candidatura, Long> {
    List<Candidatura> findByVagaId(Long vagaId);
}

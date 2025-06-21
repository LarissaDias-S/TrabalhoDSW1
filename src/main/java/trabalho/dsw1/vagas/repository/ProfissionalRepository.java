package trabalho.dsw1.vagas.repository;
import java.util.Optional;

import trabalho.dsw1.vagas.model.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {
    Optional<Profissional> findByEmail(String email);
}

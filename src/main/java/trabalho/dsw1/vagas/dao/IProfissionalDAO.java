package trabalho.dsw1.vagas.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import trabalho.dsw1.vagas.domain.Profissional;

@SuppressWarnings("unchecked")
public interface IProfissionalDAO extends CrudRepository<Profissional, Long>  {
    Profissional save(Profissional profissional);

    void deleteById(Long id);

    Profissional findByCpf(String cpf);

    Optional<Profissional> findById(Long id);
    
    List<Profissional> findAll();

    Profissional findByEmail(String username);

    default boolean isEmpty() {
        return findAll().isEmpty();
    }
}

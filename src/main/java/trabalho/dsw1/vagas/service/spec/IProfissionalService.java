package trabalho.dsw1.vagas.service.spec;

import java.util.List;

import trabalho.dsw1.vagas.domain.Profissional;

public interface IProfissionalService {

    Profissional save(Profissional profissional);

    void deleteById(Long id);

    Profissional findById(Long id);

    List<Profissional> findAll();
}

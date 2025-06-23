package trabalho.dsw1.vagas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import trabalho.dsw1.vagas.dao.IProfissionalDAO;
import trabalho.dsw1.vagas.domain.Profissional;
import trabalho.dsw1.vagas.service.spec.IProfissionalService;

@Service
@Transactional
public class ProfissionalService implements IProfissionalService {

    @Autowired
    IProfissionalDAO profissionalDAO;

    public Profissional save(Profissional profissional) {
        return profissionalDAO.save(profissional);
    }

    public void deleteById(Long id) {
        profissionalDAO.deleteById(id);
    }

    public Profissional findById(Long id) {
        return profissionalDAO.findById(id).orElse(null);
    }

    public List<Profissional> findAll() {
        return profissionalDAO.findAll();
    }
}

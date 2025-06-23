package trabalho.dsw1.vagas.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import trabalho.dsw1.vagas.dao.IProfissionalDAO;
import trabalho.dsw1.vagas.domain.Profissional;

@Component
public class UniqueCPFValidator implements ConstraintValidator<UniqueCPF, String> {

    @Autowired
    private IProfissionalDAO dao;

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (dao != null && cpf != null) {
            Profissional existente = dao.findByCpf(cpf);
            return existente == null;
        }
        return true; // permite validação no boot quando dao ainda não está disponível
    }
}

package trabalho.dsw1.vagas.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;

import trabalho.dsw1.vagas.domain.Empresa;
import trabalho.dsw1.vagas.repository.EmpresaRepository;

@Service
public class EmpresaDetailsService implements UserDetailsService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Empresa> empresaOpt = empresaRepository.findByEmail(email);

        if (empresaOpt.isEmpty()) {
            throw new UsernameNotFoundException("Empresa não encontrada com e-mail: " + email);
        }

        Empresa empresa = empresaOpt.get();

        UserBuilder builder = User.withUsername(empresa.getEmail());
        builder.password(empresa.getSenha()); // Senha criptografada
        builder.roles("EMPRESA");

        return builder.build();
    }
}

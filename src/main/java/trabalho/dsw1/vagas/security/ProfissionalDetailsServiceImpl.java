package trabalho.dsw1.vagas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import trabalho.dsw1.vagas.dao.IProfissionalDAO;
import trabalho.dsw1.vagas.domain.Profissional;

@Service
public class ProfissionalDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IProfissionalDAO profissionalDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Profissional profissional = profissionalDAO.findByEmail(username);

        if (profissional == null) {
            throw new UsernameNotFoundException("Profissional não encontrado com email: " + username);
        }

        return new ProfissionalDetails(profissional);
    }
}

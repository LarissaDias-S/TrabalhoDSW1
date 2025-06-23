package trabalho.dsw1.vagas.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import trabalho.dsw1.vagas.domain.Profissional;

import java.util.Collection;
import java.util.Collections;

public class ProfissionalDetails implements UserDetails {

    private final Profissional profissional;

    public ProfissionalDetails(Profissional profissional) {
        this.profissional = profissional;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_PROFISSIONAL"));
    }

    @Override
    public String getPassword() {
        return profissional.getPassword(); 
    }

    @Override
    public String getUsername() {
        return profissional.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Profissional getProfissional() {
        return profissional;
    }
}

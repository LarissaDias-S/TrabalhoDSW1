package trabalho.dsw1.vagas.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import trabalho.dsw1.vagas.dao.IProfissionalDAO;
import trabalho.dsw1.vagas.security.ProfissionalDetailsServiceImpl;
import trabalho.dsw1.vagas.service.EmpresaDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final EmpresaDetailsService empresaDetailsService;   
    private final ProfissionalDetailsServiceImpl profissionalDetailsService;
    private final IProfissionalDAO profissionalDAO;
    
    public WebSecurityConfig(ProfissionalDetailsServiceImpl profissionalDetailsService, EmpresaDetailsService empresaDetailsService, IProfissionalDAO profissionalDAO) {
        this.profissionalDetailsService = profissionalDetailsService;
        this.empresaDetailsService = empresaDetailsService;
        this.profissionalDAO = profissionalDAO;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public UserDetailsService adminUserDetailsService(IProfissionalDAO profissionalDAO) {
        return email -> {
            var user = profissionalDAO.findByEmail(email);
            if (user != null) {
                return User.builder()
                        .username(user.getEmail())
                        .password(user.getPassword())
                        .roles(user.getRole().replace("ROLE_", ""))
                        .build();
            } else {
                throw new UsernameNotFoundException("Usuário não encontrado: " + email);
            }
        };
    }
    @Bean
    public DaoAuthenticationProvider adminAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(adminUserDetailsService(profissionalDAO));
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public DaoAuthenticationProvider empresaAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(empresaDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public DaoAuthenticationProvider profissionalAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(profissionalDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Arrays.asList(
                adminAuthProvider(),
                empresaAuthProvider(),
                profissionalAuthProvider()
        ));
    }

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/**").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/empresa/**").hasRole("EMPRESA")
            .requestMatchers("/profissionais/**").hasRole("PROFISSIONAL")
            .requestMatchers("/vagas", "/vagas/**", "/", "/login", "/cadastro", "/css/**", "/js/**", "/images/**").permitAll()
            .anyRequest().authenticated()
        )
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(form -> form
            .loginPage("/login")
            .defaultSuccessUrl("/", true)
            .failureUrl("/login?error=true")
            .permitAll()
        )
        .logout(logout -> logout
            .logoutSuccessUrl("/login?logout")
            .permitAll()
        );
        
        return http.build();
    }
}

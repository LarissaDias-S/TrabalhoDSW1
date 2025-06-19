package trabalho.dsw1.vagas.config; // Ensure this package declaration matches your folder structure

import org.springframework.context.annotation.Bean; // 
import org.springframework.context.annotation.Configuration; // 
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // 
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // 
import org.springframework.security.core.userdetails.User; // 
import org.springframework.security.core.userdetails.UserDetails; // 
import org.springframework.security.core.userdetails.UserDetailsService; // 
import org.springframework.security.provisioning.InMemoryUserDetailsManager; // 
import org.springframework.security.web.SecurityFilterChain; // 

@Configuration // 
@EnableWebSecurity // 
public class SecurityConfig {

    @Bean // 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/public/**", "/login").permitAll() //acesso dessas paginas para todos
                .requestMatchers("/login","/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()//permitir acesso para todos
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
            )
            .logout(logout -> logout
                .permitAll());
        return http.build();
    }

    @Bean // 
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername("admin@admin.com")
            .password("{noop}123456") // {noop} para senha em texto
            .roles("ADMIN") //criar essa função para que apenas pessoas ADMIN tenho acesso a CRUD de empresas
            .build();
        return new InMemoryUserDetailsManager(admin); // 
    }
}
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
                .requestMatchers("/public/**", "/login").permitAll()
                .requestMatchers("/login","/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
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
            .password("{noop}123456") // {noop} for plain text password - DO NOT USE IN PRODUCTION
            .roles("ADMIN")
            .build();
        return new InMemoryUserDetailsManager(admin); // 
    }
}
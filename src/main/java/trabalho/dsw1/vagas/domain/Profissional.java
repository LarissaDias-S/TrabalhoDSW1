package trabalho.dsw1.vagas.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import trabalho.dsw1.vagas.validation.UniqueCPF;

@SuppressWarnings("serial")
@Entity
public class Profissional extends AbstractEntity<Long> {
    
    @NotBlank
    @Column(nullable = false, length = 64, unique = true)
    private String email;

    @NotBlank
    @Column(nullable = false, length = 64)
    private String password;

    @NotBlank
    @Column(nullable = false, length = 60)
    private String nome;

    @UniqueCPF
    @NotBlank
    @Column(nullable = false, length = 14, unique = true)
    private String cpf;

    @NotBlank
    @Column(nullable = false, length = 32)
    private String role;

    @Column(nullable = false)
    private boolean enabled;

    // Getters e Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

package trabalho.dsw1.vagas.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.*;

import trabalho.dsw1.vagas.validation.UniqueCPF;

@SuppressWarnings("serial")
@Entity
public class Profissional extends AbstractEntity<Long> {
    
    @NotBlank(message = "{Profissional.email.NotBlank}")
    @Email(message = "{Profissional.email.Email}")
    @Size(max = 64, message = "{Profissional.email.Size}")
    @Column(nullable = false, length = 64, unique = true)
    private String email;

    @NotBlank(message = "{Profissional.password.NotBlank}")
    @Size(min = 6, max = 64, message = "{Profissional.password.Size}")
    @Column(nullable = false, length = 64)
    private String password;

    @NotBlank(message = "{Profissional.nome.NotBlank}")
    @Size(min = 2, max = 60, message = "{Profissional.nome.Size}")
    @Column(nullable = false, length = 60)
    private String nome;

    @NotBlank(message = "{Profissional.cpf.NotBlank}")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "{Profissional.cpf.Pattern}")
    @UniqueCPF(message = "{Profissional.cpf.UniqueCPF}") // Sua anotação customizada também usa a chave
    @Column(nullable = false, length = 14, unique = true)
    private String cpf;

    @NotBlank(message = "{Profissional.role.NotBlank}")
    @Pattern(regexp = "ROLE_USER|ROLE_ADMIN|ROLE_EMPRESA|ROLE_PROFISSIONAL", message = "{Profissional.role.Pattern}")
    @Column(nullable = false, length = 32)
    private String role;

    @NotBlank(message = "{Profissional.telefone.NotBlank}")
    @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message = "{Profissional.telefone.Pattern}")
    @Column(nullable = false, length = 15)
    private String telefone;

    @NotBlank(message = "{Profissional.sexo.NotBlank}")
    @Pattern(regexp = "M|F|OUTRO", message = "{Profissional.sexo.Pattern}")
    @Column(nullable = false, length = 6)
    private String sexo;

    @NotNull(message = "{Profissional.dataNascimento.NotNull}")
    @Past(message = "{Profissional.dataNascimento.Past}")
    @Column(nullable = false)
    private LocalDate dataNascimento;

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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public boolean isEmpty() {
        return email == null && password == null && nome == null && cpf == null && role == null && telefone == null && sexo == null;
    }

}

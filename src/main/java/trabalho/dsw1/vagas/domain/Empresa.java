package trabalho.dsw1.vagas.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.*;

@Entity
public class Empresa extends AbstractEntity<Long> {

    @NotBlank(message = "{Empresa.email.NotBlank}")
    @Email(message = "{Empresa.email.Email}")
    @Size(max = 64, message = "{Empresa.email.Size}")
    @Column(nullable = false, length = 64, unique = true)
    private String email;

    @NotBlank(message = "{Empresa.senha.NotBlank}")
    @Size(min = 6, max = 64, message = "{Empresa.senha.Size}")
    @Column(nullable = false, length = 64, unique = true)
    private String senha;

    @NotBlank(message = "{Empresa.cnpj.NotBlank}")
    @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}", message = "{Empresa.cnpj.Pattern}")
    @Size(max = 18, message = "{Empresa.cnpj.Size}")
    @Column(nullable = false, length = 64, unique = true)
    private String cnpj;

    @NotBlank(message = "{Empresa.nome.NotBlank}")
    @Size(min = 2, max = 64, message = "{Empresa.nome.Size}")
    @Column(nullable = false, length = 64, unique = true)
    private String nome;

    @NotBlank(message = "{Empresa.descricao.NotBlank}")
    @Size(max = 256, message = "{Empresa.descricao.Size}")
    @Column(nullable = false, length = 256)
    private String descricao;

    @NotBlank(message = "{Empresa.cidade.NotBlank}")
    @Size(max = 64, message = "{Empresa.cidade.Size}")
    @Column(nullable = false, length = 64)
    private String cidade;


    // getters e setters
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
}

package trabalho.dsw1.vagas.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Empresa extends AbstractEntity<Long> {

    @NotBlank
    @Column(nullable = false, length = 64, unique = true)
    private String email;

    @NotBlank
    @Column(nullable = false, length = 64, unique = true)
    private String senha; 

    @NotBlank
    @Column(nullable = false, length = 64, unique = true)
    private String cnpj;

    @NotBlank
    @Column(nullable = false, length = 64, unique = true)
    private String nome;

    @NotBlank
    @Column(nullable = false, length = 256, unique = false)
    private String descricao;

    @NotBlank
    @Column(nullable = false, length = 64, unique = false)
    private String cidade;

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

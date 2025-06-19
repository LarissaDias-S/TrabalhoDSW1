package trabalho.dsw1.vagas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String senha; // In a real app, store hashed password
    private String cnpj;
    private String nome;
    private String descricao;
    private String cidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    // Opcional: toString() para facilitar o debug
    @Override
    public String toString() {
        return "Empresa{" +
               "id=" + id +
               ", email='" + email + '\'' +
               ", cnpj='" + cnpj + '\'' +
               ", nome='" + nome + '\'' +
               ", descricao='" + descricao + '\'' +
               ", cidade='" + cidade + '\'' +
               '}';
    }
}

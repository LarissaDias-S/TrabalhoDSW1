package trabalho.dsw1.vagas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity

public class Profissional {
    @Id
    private String nome;
    private String email;
    private String senha;

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getNome() {
        return nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getSenha() {
        return senha;
    }
    
}

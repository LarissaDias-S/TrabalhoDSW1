<<<<<<< HEAD:src/main/java/trabalho/dsw1/vagas/domain/Vaga.java
package trabalho.dsw1.vagas.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Vaga extends AbstractEntity<Long> {

    @NotBlank
    @Column(nullable = false, length = 64, unique = true)
    private String titulo;

    @NotBlank
    @Column(nullable = false, length = 256, unique = true)
    private String descricao;

    @NotBlank
    @Column(nullable = false, length = 64, unique = true)
    private String localizacao;

    @NotBlank
    @Column(nullable = false, length = 64, unique = false)
    private String tipoContrato; 

    @Column(nullable = false, length = 64, unique = false)
    private double salario;

    @Column(nullable = false, length = 64, unique = false)
    private boolean remoto; 

    @ManyToOne(optional = false)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    // Getters e Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public boolean isRemoto() {
        return remoto;
    }

    public void setRemoto(boolean remoto) {
        this.remoto = remoto;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
}
=======
package trabalho.dsw1.vagas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import jakarta.persistence.ManyToOne;

@Entity
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private Double remuneracao;
    private LocalDate dataLimite;

    @ManyToOne //relacionamento "muitos pra um" entre vaga e empresa, varias vagas pertencem a UMA empresa
    private Empresa empresa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getRemuneracao() {
        return remuneracao;
    }

    public void setRemuneracao(Double remuneracao) {
        this.remuneracao = remuneracao;
    }

    public LocalDate getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(LocalDate dataLimite) {
        this.dataLimite = dataLimite;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

}
>>>>>>> 3bf467c (R8 e mudancas no R6):src/main/java/trabalho/dsw1/vagas/model/Vaga.java

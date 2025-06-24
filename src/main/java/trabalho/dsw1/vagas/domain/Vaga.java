package trabalho.dsw1.vagas.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
public class Vaga extends AbstractEntity<Long> {

    @OneToMany(mappedBy = "vaga", cascade = CascadeType.REMOVE)
    private List<Candidato> candidatos;

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

    @Column(nullable = false, length = 64, unique = false)
    private LocalDate dataLimite; 

    @ManyToOne(optional = false, cascade = CascadeType.REMOVE)
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

    public LocalDate getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(LocalDate dataLimite) {
        this.dataLimite = dataLimite;
    }

    public List<Candidato> getCandidatos() {
        return candidatos;
    }

    public void setCandidatos(List<Candidato> candidatos) {
        this.candidatos = candidatos;
    }
    
}

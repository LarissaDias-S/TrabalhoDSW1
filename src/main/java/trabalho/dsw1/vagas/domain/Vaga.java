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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
public class Vaga extends AbstractEntity<Long> {

    @OneToMany(mappedBy = "vaga", cascade = CascadeType.REMOVE)
    private List<Candidato> candidatos;

    @NotBlank(message = "O título é obrigatório")
    @Size(max = 64, message = "O título deve ter no máximo 64 caracteres")
    @Column(nullable = false, length = 64)
    private String titulo;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 256, message = "A descrição deve ter no máximo 256 caracteres")
    @Column(nullable = false, length = 256)
    private String descricao;

    @NotBlank(message = "A localização é obrigatória")
    @Size(max = 64, message = "A localização deve ter no máximo 64 caracteres")
    @Column(nullable = false, length = 64)
    private String localizacao;

    @NotBlank(message = "O tipo de contrato é obrigatório")
    @Size(max = 64, message = "O tipo de contrato deve ter no máximo 64 caracteres")
    @Column(nullable = false, length = 64)
    private String tipoContrato; 

    @PositiveOrZero(message = "O salário deve ser zero ou positivo")
    @Column(nullable = false)
    private double salario;

    @Column(nullable = false)
    private boolean remoto; 

    @NotNull(message = "A data limite é obrigatória")
    @FutureOrPresent(message = "A data limite deve ser hoje ou futura")
    @Column(nullable = false)
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

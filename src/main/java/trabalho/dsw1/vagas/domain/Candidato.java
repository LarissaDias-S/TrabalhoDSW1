package trabalho.dsw1.vagas.domain;

import java.time.LocalDateTime;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;

@Entity
public class Candidato extends AbstractEntity<Long> {

    @NotNull(message = "O profissional é obrigatório")
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "profissional_id", nullable = false)
    private Profissional profissional;

    @NotNull(message = "A vaga é obrigatória")
    @ManyToOne
    @JoinColumn(name = "vaga_id", nullable = false)
    private Vaga vaga;

    @NotNull(message = "O currículo é obrigatório")
    @Size(min = 1, message = "O currículo não pode estar vazio")
    @Column(nullable = false, columnDefinition = "LONGBLOB")
    private byte[] curriculo;

    @NotBlank(message = "O status é obrigatório")
    @Size(max = 64, message = "O status deve ter no máximo 64 caracteres")
    @Pattern(regexp = "ABERTO|NAO_SELECIONADO|ENTREVISTA", message = "Status inválido")
    @Column(nullable = false, length = 64)
    private String status = "ABERTO";

    @Size(max = 256, message = "O link da entrevista deve ter no máximo 256 caracteres")
    @Pattern(regexp = "^(https?://.*)?$", message = "Link da entrevista deve ser uma URL válida")
    @Column(length = 256)
    private String linkEntrevista;

    @FutureOrPresent(message = "A data/hora da entrevista deve ser no futuro ou presente")
    @Column(nullable = true)
    private LocalDateTime dataHoraEntrevista;
    
    public Candidato() {
    }

    public Candidato(Profissional profissional, Vaga vaga, byte[] curriculo, String status) {
        this.profissional = profissional;
        this.vaga = vaga;
        this.curriculo = curriculo;
        this.status = status;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

    public Vaga getVaga() {
        return vaga;
    }

    public void setVaga(Vaga vaga) {
        this.vaga = vaga;
    }

    public byte[] getCurriculo() {
        return curriculo;
    }

    public void setCurriculo(byte[] curriculo) {
        this.curriculo = curriculo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLinkEntrevista() {
        return linkEntrevista;
    }

    public void setLinkEntrevista(String linkEntrevista) {
        this.linkEntrevista = linkEntrevista;
    }

    public LocalDateTime getDataHoraEntrevista() {
        return dataHoraEntrevista;
    }

    public void setDataHoraEntrevista(LocalDateTime dataHoraEntrevista) {
        this.dataHoraEntrevista = dataHoraEntrevista;
    }

}

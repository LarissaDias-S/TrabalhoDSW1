package trabalho.dsw1.vagas.domain;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Candidato extends AbstractEntity<Long> {

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "profissional_id", nullable = false)
    private Profissional profissional;

    @ManyToOne
    @JoinColumn(name = "vaga_id", nullable = false)
    private Vaga vaga;

    @Column(nullable = false, columnDefinition = "LONGBLOB")
    private byte[] curriculo;
    @Column(nullable = false, length = 64, unique = false)
    private String status = "Ativo";

    @Column(length = 256, unique = false)
    private String linkEntrevista;

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

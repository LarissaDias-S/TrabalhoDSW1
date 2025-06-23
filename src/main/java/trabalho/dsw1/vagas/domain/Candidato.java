package trabalho.dsw1.vagas.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Candidato extends AbstractEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "profissional_id", nullable = false)
    private Profissional profissional;

    @ManyToOne
    @JoinColumn(name = "vaga_id", nullable = false)
    private Vaga vaga;

    @Column(nullable = false, columnDefinition = "LONGBLOB")
    private byte[] curriculo;
    
    public Candidato() {
    }

    public Candidato(Profissional profissional, Vaga vaga, byte[] curriculo) {
        this.profissional = profissional;
        this.vaga = vaga;
        this.curriculo = curriculo;
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
    
}

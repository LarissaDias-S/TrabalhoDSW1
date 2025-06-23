package trabalho.dsw1.vagas.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Candidatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Vaga vaga;

    @ManyToOne //Cada candidatura está ligada a uma única vaga e um único profissional
    private Profissional profissional;

    private String status; // ABERTO, NAO_SELECIONADO, ENTREVISTA

    private String linkEntrevista;
    private LocalDateTime dataHoraEntrevista;

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vaga getVaga() {
        return vaga;
    }

    public void setVaga(Vaga vaga) {
        this.vaga = vaga;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
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

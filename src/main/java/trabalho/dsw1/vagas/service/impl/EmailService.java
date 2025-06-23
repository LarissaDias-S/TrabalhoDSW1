package trabalho.dsw1.vagas.service.impl;

import trabalho.dsw1.vagas.domain.Candidato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarResultado(Candidato candidatura) {
        String para = candidatura.getProfissional().getEmail();
        String assunto = "Resultado da sua candidatura";
        String corpo;

        if ("ENTREVISTA".equals(candidatura.getStatus())) {
            corpo = """
                    Você foi selecionado(a) para uma entrevista!

                    Link: %s
                    Data/Hora: %s
                    """.formatted(candidatura.getLinkEntrevista(), candidatura.getDataHoraEntrevista());
        } else {
            corpo = "Infelizmente você não foi selecionado(a) para a vaga. Agradecemos sua participação!";
        }

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(para);
        mensagem.setSubject(assunto);
        mensagem.setText(corpo);

        mailSender.send(mensagem);
    }
}
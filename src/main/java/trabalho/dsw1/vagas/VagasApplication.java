package trabalho.dsw1.vagas;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import trabalho.dsw1.vagas.dao.IProfissionalDAO;
import trabalho.dsw1.vagas.dao.IVagaDAO;
import trabalho.dsw1.vagas.domain.Empresa;
import trabalho.dsw1.vagas.domain.Profissional;
import trabalho.dsw1.vagas.domain.Vaga;
import trabalho.dsw1.vagas.repository.EmpresaRepository;

@SpringBootApplication
public class VagasApplication {

	public static void main(String[] args) {
		SpringApplication.run(VagasApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(IProfissionalDAO profissionalDAO, EmpresaRepository empresaRepository, IVagaDAO vagaDAO) {
    return (args) -> {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		String email = "admin@admin.com";

		Profissional admin = new Profissional();
		admin.setEmail(email);
		admin.setPassword(encoder.encode("123456"));
		admin.setNome("Administrador");
		admin.setCpf("000.000.000-00");
		admin.setRole("ROLE_ADMIN");
		admin.setEnabled(true);
		admin.setSexo("OUTRO"); 
		admin.setTelefone("(11) 99999-9999");
		admin.setDataNascimento(LocalDate.of(2000, 1, 1)); 

		profissionalDAO.save(admin);

        Profissional p1 = new Profissional();
        p1.setEmail("joao.silva@gmail.com");
        p1.setPassword(encoder.encode("senha123"));
        p1.setNome("João Silva");
        p1.setCpf("123.456.789-00");
        p1.setEnabled(true);
		p1.setTelefone("(11) 91234-5678");
		p1.setSexo("M");
		p1.setDataNascimento(LocalDate.of(1995, 5, 20)); 
		p1.setRole("ROLE_PROFISSIONAL");

        Profissional p2 = new Profissional();
        p2.setEmail("maria.souza@gmail.com");
        p2.setPassword(encoder.encode("senha456"));
        p2.setNome("Maria Souza");
        p2.setCpf("987.654.321-11");
        p2.setEnabled(true);
		p2.setTelefone("(19) 99876-5432");
		p2.setSexo("F");
		p2.setDataNascimento(LocalDate.of(1990, 8, 15));
		p2.setRole("ROLE_PROFISSIONAL");


		Empresa e1 = new Empresa();
        e1.setEmail("contato@techcorp.com");
        e1.setSenha(encoder.encode("tech123")); // criptografar se necessário
        e1.setCnpj("12.345.678/0001-90");
        e1.setNome("TechCorp Soluções");
        e1.setDescricao("Empresa especializada em soluções de tecnologia para negócios.");
        e1.setCidade("São Paulo");

        Empresa e2 = new Empresa();
        e2.setEmail("rh@biolab.com");
        e2.setSenha(encoder.encode("biolab456")); // criptografar se necessário
        e2.setCnpj("98.765.432/0001-12");
        e2.setNome("BioLab Pesquisas");
        e2.setDescricao("Laboratório de pesquisas biotecnológicas com atuação nacional.");
        e2.setCidade("Campinas");

        empresaRepository.save(e1);
        empresaRepository.save(e2);
        profissionalDAO.save(p1);
        profissionalDAO.save(p2);

		Empresa empresa1 = empresaRepository.findByEmail("contato@techcorp.com").orElseThrow();
        Empresa empresa2 = empresaRepository.findByEmail("rh@biolab.com").orElseThrow();

        Vaga v1 = new Vaga();
        v1.setTitulo("Desenvolvedor Backend");
        v1.setDescricao("Responsável por desenvolver APIs REST com Java e Spring.");
        v1.setLocalizacao("São Paulo");
        v1.setTipoContrato("CLT");
        v1.setSalario(6500.00);
        v1.setRemoto(true);
        v1.setDataLimite(LocalDate.now().plusDays(30));
        v1.setEmpresa(empresa1);

        Vaga v2 = new Vaga();
        v2.setTitulo("Pesquisador Júnior");
        v2.setDescricao("Atuar com pesquisa laboratorial em biotecnologia.");
        v2.setLocalizacao("Campinas");
        v2.setTipoContrato("Estágio");
        v2.setSalario(1800.00);
        v2.setRemoto(false);
        v2.setDataLimite(LocalDate.now().plusDays(15));
        v2.setEmpresa(empresa2);

        vagaDAO.save(v1);
        vagaDAO.save(v2);
    };
}

}

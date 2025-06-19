package trabalho.dsw1.vagas.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import trabalho.dsw1.vagas.model.Admin;
import trabalho.dsw1.vagas.repository.AdminRepository;

@Component
public class AdminSeeder implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public void run(String... args) throws Exception {
        if (adminRepository.findByEmail("admin@admin.com") == null) {
            Admin admin = new Admin();
            admin.setEmail("admin@admin.com");
            admin.setSenha("123456");
            adminRepository.save(admin);
        }
    }
}

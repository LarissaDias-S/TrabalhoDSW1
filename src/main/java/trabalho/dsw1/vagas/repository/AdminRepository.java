package trabalho.dsw1.vagas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trabalho.dsw1.vagas.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByEmail(String email);
}

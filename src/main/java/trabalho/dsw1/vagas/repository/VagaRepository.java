package trabalho.dsw1.vagas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trabalho.dsw1.vagas.model.Vaga;
import trabalho.dsw1.vagas.model.Empresa;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface VagaRepository extends JpaRepository<Vaga, Long> {

    @Query("SELECT v FROM Vaga v WHERE v.dataLimite >= :hoje")
    List<Vaga> findVagasAbertas(@Param("hoje") LocalDate hoje);

    @Query("SELECT v FROM Vaga v WHERE v.dataLimite >= :hoje AND LOWER(v.empresa.cidade) LIKE LOWER(CONCAT('%', :cidade, '%'))")
    List<Vaga> findVagasAbertasPorCidade(@Param("hoje") LocalDate hoje, @Param("cidade") String cidade);
    
    List<Vaga> findByEmpresa(Empresa empresa);

}

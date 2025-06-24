package trabalho.dsw1.vagas.dao;

import java.util.Optional;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import trabalho.dsw1.vagas.domain.Vaga;

@SuppressWarnings("unchecked")
public interface IVagaDAO extends CrudRepository<Vaga, Long>  {
    Vaga save(Vaga Vaga);

    void deleteById(Long id);

    Optional<Vaga> findById(Long id);
    
    List<Vaga> findAll();

    List<Vaga> findByEmpresaId(Long empresaId);

    @Query("SELECT v FROM Vaga v WHERE v.id NOT IN (SELECT c.vaga.id FROM Candidato c WHERE c.profissional.id = :profissionalId) AND v.dataLimite >= :dataHoje")
    List<Vaga> findVagasNaoCandidatadas(Long profissionalId, LocalDate dataHoje);

    @Query("SELECT v FROM Vaga v WHERE v.id IN (SELECT c.vaga.id FROM Candidato c WHERE c.profissional.id = :profissionalId)")
    List<Vaga> findVagasCandidatadas(Long profissionalId);

    @Query("SELECT v FROM Vaga v WHERE v.localizacao = :cidade")
    List<Vaga> findVagasAbertasPorCidade(@Param("cidade") String cidade, List<Vaga> vagas);

    @Query("SELECT v FROM Vaga v WHERE v.dataLimite >= :dataHoje")
    List<Vaga> buscarVagasValidas(LocalDate dataHoje); 

    @Query("SELECT v FROM Vaga v WHERE v.id NOT IN (SELECT c.vaga.id FROM Candidato c WHERE c.profissional.id = :profissionalID)")
    List<Vaga> findVagasNaoCandidatadasSemData(Long profissionalID);

    @Query("SELECT v FROM Vaga v WHERE v.localizacao = :cidade AND v.id NOT IN (SELECT c.vaga.id FROM Candidato c WHERE c.profissional.id = :profissionalId) AND v.dataLimite >= :dataHoje")
    List<Vaga> findVagasAbertasPorCidade(@Param("cidade") String cidade, Long profissionalId, LocalDate dataHoje);

    @Query("SELECT v FROM Vaga v WHERE v.localizacao = :cidade AND v.dataLimite >= :dataHoje")
    List<Vaga> findVagasAbertasPorCidadeSemLogar(@Param("cidade") String cidade, LocalDate dataHoje);
}

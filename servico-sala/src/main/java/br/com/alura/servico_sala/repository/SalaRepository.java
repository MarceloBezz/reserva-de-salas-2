package br.com.alura.servico_sala.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.alura.servico_sala.model.sala.Sala;

//TODO Criar um método para buscar as reservas de uma sala no ms de reservas
@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {
// TODO: Fazer esse método no microsserviço de Reservas
    //    @Query("""
//                SELECT s FROM Sala s
//                WHERE s.ativa = true
//                AND NOT EXISTS (
//                    SELECT r FROM Reserva r
//                    WHERE r.sala = s
//                    AND r.inicio < :fim
//                    AND r.fim > :inicio
//                )
//            """)
//    List<Sala> findSalasDisponiveis(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}

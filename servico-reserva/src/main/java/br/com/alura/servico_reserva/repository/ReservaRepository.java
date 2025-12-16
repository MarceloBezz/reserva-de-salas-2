package br.com.alura.servico_reserva.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.servico_reserva.model.Reserva.Reserva;
import br.com.alura.servico_reserva.model.Reserva.ReservaStatus;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long>{
    boolean existsBySalaIdAndInicioLessThanAndFimGreaterThanAndStatus(Long salaId, LocalDateTime fimDesejado, LocalDateTime inicioDesejado, ReservaStatus status);
    
//    @Query("SELECT r FROM Reserva r JOIN FETCH r.usuario WHERE r.id = :id")
    // Reserva findByIdComUsuario(@Param("id") Long id);

    List<Reserva> findByUsuarioIdAndInicioAfter(Long usuarioId, LocalDateTime agora);
    List<Reserva> findByUsuarioIdAndInicioBefore(Long usuarioId, LocalDateTime agora);
}

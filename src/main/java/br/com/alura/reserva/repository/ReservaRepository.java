package br.com.alura.reserva.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.alura.reserva.model.Reserva.Reserva;
import br.com.alura.reserva.model.Reserva.ReservaStatus;
import br.com.alura.reserva.model.Sala.Sala;
import br.com.alura.reserva.model.Usuario.Usuario;


@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long>{
    boolean existsBySalaAndInicioLessThanAndFimGreaterThanAndStatus(Sala sala, LocalDateTime fimDesejado, LocalDateTime inicioDesejado, ReservaStatus status);
    
    @Query("SELECT r FROM Reserva r JOIN FETCH r.usuario WHERE r.id = :id")
    Reserva findByIdComUsuario(@Param("id") Long id);

    List<Reserva> findByUsuarioAndInicioAfter(Usuario usuario, LocalDateTime agora);
    List<Reserva> findByUsuarioAndInicioBefore(Usuario usuario, LocalDateTime agora);
}

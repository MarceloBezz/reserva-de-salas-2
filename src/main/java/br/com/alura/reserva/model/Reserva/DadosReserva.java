package br.com.alura.reserva.model.Reserva;

import java.time.LocalDateTime;

import br.com.alura.reserva.model.Sala.DadosSala;
import br.com.alura.reserva.model.Sala.Sala;

public record DadosReserva(
    Long id,
    Long usuarioId,
    DadosSala sala,
    LocalDateTime inicio,
    LocalDateTime fim,
    int quantidade,
    ReservaStatus status
) {
    public DadosReserva(Reserva reserva, Long usuarioId, Sala sala) {
        this(reserva.getId(), usuarioId, new DadosSala(sala),reserva.getInicio(),reserva.getFim(),reserva.getQuantidade(),reserva.getStatus());
    }
}

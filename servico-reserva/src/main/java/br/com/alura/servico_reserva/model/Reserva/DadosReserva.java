package br.com.alura.servico_reserva.model.Reserva;

import java.time.LocalDateTime;

public record DadosReserva(
    Long id,
    Long usuarioId,
    Long sala,
    LocalDateTime inicio,
    LocalDateTime fim,
    int quantidade,
    ReservaStatus status
) {
    public DadosReserva(Reserva reserva, Long usuarioId, Long salaId) {
        this(reserva.getId(), usuarioId, salaId,reserva.getInicio(),reserva.getFim(),reserva.getQuantidade(),reserva.getStatus());
    }
}

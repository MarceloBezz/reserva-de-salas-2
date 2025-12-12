package br.com.alura.reserva.validacoes;

import br.com.alura.reserva.model.Reserva.ReservaDTO;
import br.com.alura.reserva.model.Sala.Sala;

public interface IValidacaoReserva {
    void validar(Sala sala, ReservaDTO dto);
}

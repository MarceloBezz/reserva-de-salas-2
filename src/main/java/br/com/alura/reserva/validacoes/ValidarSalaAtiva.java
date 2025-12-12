package br.com.alura.reserva.validacoes;

import org.springframework.stereotype.Component;

import br.com.alura.reserva.infra.exception.RegraDeNegocioException;
import br.com.alura.reserva.model.Reserva.ReservaDTO;
import br.com.alura.reserva.model.Sala.Sala;

@Component
public class ValidarSalaAtiva implements IValidacaoReserva{

    @Override
    public void validar(Sala sala, ReservaDTO dto) {
        if (!sala.isAtiva())
            throw new RegraDeNegocioException("A sala desejada não está ativa! Favor selecionar outra que esteja");
    }
    
}

package br.com.alura.reserva.validacoes;

import org.springframework.stereotype.Component;

import br.com.alura.reserva.infra.exception.RegraDeNegocioException;
import br.com.alura.reserva.model.Reserva.ReservaDTO;
import br.com.alura.reserva.model.Sala.Sala;

@Component
public class ValidarCapacidadeSala implements IValidacaoReserva{

    @Override
    public void validar(Sala sala, ReservaDTO dto) {
        if  (sala.getCapacidade() < dto.quantidade())
            throw new RegraDeNegocioException("Capacidade da sala excedida!");
    }
    
}

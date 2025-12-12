package br.com.alura.reserva.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.reserva.infra.exception.RegraDeNegocioException;
import br.com.alura.reserva.model.Reserva.DadosReserva;
import br.com.alura.reserva.model.Reserva.Reserva;
import br.com.alura.reserva.model.Reserva.ReservaDTO;
import br.com.alura.reserva.model.Reserva.ReservaStatus;
import br.com.alura.reserva.model.Sala.Sala;
import br.com.alura.reserva.model.Usuario.Usuario;
import br.com.alura.reserva.repository.ReservaRepository;
import br.com.alura.reserva.repository.SalaRepository;
import br.com.alura.reserva.validacoes.IValidacaoReserva;
import jakarta.transaction.Transactional;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository repository;

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private List<IValidacaoReserva> validadores;

    public Reserva agendarReserva(ReservaDTO dados, Usuario usuario) {
        Sala salaDesejada = salaRepository.findById(dados.salaId())
                .orElseThrow(() -> new RegraDeNegocioException("Sala não encontrada!"));

        validadores.forEach(v -> v.validar(salaDesejada, dados));

        Reserva reserva = new Reserva(dados, usuario, salaDesejada);
        return repository.save(reserva);
    }

    public DadosReserva buscarReserva(Long idReserva, Usuario usuario) {
        Reserva reserva = repository.findByIdComUsuario(idReserva);
        if (!reserva.getUsuario().getId().equals(usuario.getId()))
            throw new RegraDeNegocioException("Você não tem acesso a essa reserva!");

        return new DadosReserva(reserva, usuario.getId(), reserva.getSala());
    }

    public HashMap<String, List<DadosReserva>> buscarTodasReservas(Usuario usuario) {
        var reservasFuturas = repository
                .findByUsuarioAndInicioAfter(usuario, LocalDateTime.now())
                .stream()
                .map(reserva -> new DadosReserva(reserva, usuario.getId(), reserva.getSala()))
                .toList();
        var reservasPassadas = repository
                .findByUsuarioAndInicioBefore(usuario, LocalDateTime.now())
                .stream()
                .map(reserva -> new DadosReserva(reserva, usuario.getId(), reserva.getSala()))
                .toList();

        HashMap<String, List<DadosReserva>> reservas = new HashMap<>();
        reservas.put("ProximasReservas", reservasFuturas);
        reservas.put("ReservasPassadas", reservasPassadas);
        return reservas;
    }

    @Transactional
    public void cancelarReserva(Long id, Usuario usuario) {
        Reserva reserva = repository.findByIdComUsuario(id);
        if (!reserva.getUsuario().getId().equals(usuario.getId()))
            throw new RegraDeNegocioException("Você não tem acesso a essa reserva!");

        if (reserva.getStatus() == ReservaStatus.CANCELADA)
            throw new RegraDeNegocioException("Esta reserva já está cancelada!");
        
        reserva.setStatus(ReservaStatus.CANCELADA);
    }
}

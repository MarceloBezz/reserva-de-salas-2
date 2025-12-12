package br.com.alura.reserva.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.reserva.infra.exception.RegraDeNegocioException;
import br.com.alura.reserva.model.Sala.DadosSala;
import br.com.alura.reserva.model.Sala.HorarioSalaDTO;
import br.com.alura.reserva.model.Sala.Sala;
import br.com.alura.reserva.model.Sala.SalaDTO;
import br.com.alura.reserva.repository.SalaRepository;
import jakarta.transaction.Transactional;

@Service
public class SalaService {
    @Autowired
    private SalaRepository repository;

    public Sala criarSala(SalaDTO dto) {
        Sala sala = new Sala(dto);
        return repository.save(sala);
    }

    public List<DadosSala> listarSalasDisponiveis(HorarioSalaDTO dados) {
        List<Sala> salas = repository.findSalasDisponiveis(dados.inicio(), dados.fim());
        return salas.stream().map(DadosSala::new).toList();
    }

    @Transactional
    public void desativarSala(Long id) {
        Sala sala = repository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Forneça um id válido!"));
        sala.setAtiva(false);
    }

    public List<DadosSala> buscarSalas() {
        return repository
                .findAll()
                .stream()
                .map(DadosSala::new)
                .toList();
    }

    public DadosSala buscarPorId(Long id) {
        Sala sala = repository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Forneça um id válido!"));
        return new DadosSala(sala);
    }
}

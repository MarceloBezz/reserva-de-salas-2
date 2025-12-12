package br.com.alura.reserva.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.reserva.model.Sala.DadosSala;
import br.com.alura.reserva.model.Sala.HorarioSalaDTO;
import br.com.alura.reserva.model.Sala.SalaDTO;
import br.com.alura.reserva.service.SalaService;
import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/sala")
public class SalaController {

    @Autowired
    private SalaService service;

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarSala(@RequestBody @Valid SalaDTO dados, UriComponentsBuilder uriBuilder) {
        var sala = service.criarSala(dados);
        URI uri = uriBuilder.path("/sala/buscar/{id}")
                .buildAndExpand(sala.getId())
                .toUri();
        return ResponseEntity.created(uri).body("Sala criada com sucesso!");
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<DadosSala> buscarPorId(@PathVariable Long id) {
        var sala = service.buscarPorId(id);
        return ResponseEntity.ok().body(sala);
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<DadosSala>> buscarSalasDisponiveisPorHorario(@RequestBody HorarioSalaDTO dados) {
        var salasDisponiveis = service.listarSalasDisponiveis(dados);
        return ResponseEntity.ok().body(salasDisponiveis);
    }

    @GetMapping("/todas")
    public ResponseEntity<List<DadosSala>> buscarSalas() {
        var salas = service.buscarSalas();
        return ResponseEntity.ok().body(salas);
    }

    @PatchMapping("/desativar/{id}")
    public ResponseEntity<String> desativarSala(@PathVariable Long id) {
        service.desativarSala(id);
        return ResponseEntity.ok().body("Sala desativada!");
    }
}

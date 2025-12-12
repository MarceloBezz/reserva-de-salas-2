package br.com.alura.reserva.controller;

import org.springframework.web.bind.annotation.RestController;

import br.com.alura.reserva.model.Usuario.UsuarioCadastroDTO;
import br.com.alura.reserva.service.UsuarioService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @PostMapping("/cadastrar")
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid UsuarioCadastroDTO dto) {
        try {
            var usuario = service.cadastrar(dto);
            return ResponseEntity.ok().body(usuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}

package br.com.alura.reserva.model.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioCadastroDTO(
    @NotBlank String nome,
    @NotBlank @Email String email,
    @NotBlank String senha,
    String telefone
) {
    
}

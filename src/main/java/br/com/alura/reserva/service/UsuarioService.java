package br.com.alura.reserva.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.alura.reserva.model.Usuario.DadosUsuario;
import br.com.alura.reserva.model.Usuario.RoleNome;
import br.com.alura.reserva.model.Usuario.Usuario;
import br.com.alura.reserva.model.Usuario.UsuarioCadastroDTO;
import br.com.alura.reserva.repository.RoleRepository;
import br.com.alura.reserva.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
    }

    public DadosUsuario cadastrar(UsuarioCadastroDTO dto) throws Exception {
        String senhaCriptografada = encoder.encode(dto.senha());
        var emailJaCadastrado = repository.findByEmailIgnoreCase(dto.email());

        if (emailJaCadastrado.isPresent()) {
            throw new Exception("Usuário já cadastrado!");
        }

        var role = roleRepository.findByNome(RoleNome.CLIENTE);
        Usuario usuario = new Usuario(dto, senhaCriptografada, role);
        repository.save(usuario);
        return new DadosUsuario(usuario);
    }

}

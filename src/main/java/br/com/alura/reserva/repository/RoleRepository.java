package br.com.alura.reserva.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.reserva.model.Usuario.RoleNome;
import br.com.alura.reserva.model.Usuario.Roles;

public interface RoleRepository extends JpaRepository<Roles, Long>{
    Roles findByNome(RoleNome nome);
}

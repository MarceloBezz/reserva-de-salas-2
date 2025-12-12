package br.com.alura.reserva.model.Reserva;

import java.time.LocalDateTime;

import br.com.alura.reserva.model.Sala.Sala;
import br.com.alura.reserva.model.Usuario.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reservas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sala_id")
    private Sala sala;
    private LocalDateTime inicio;
    private LocalDateTime fim;

    @Enumerated(EnumType.STRING)
    private ReservaStatus status;
    private int quantidade;

    public Reserva(ReservaDTO dados, Usuario usuario, Sala salaDesejada) {
        this.usuario = usuario;
        this.sala = salaDesejada;
        this.inicio = dados.inicio();
        this.fim = dados.fim();
        this.status = ReservaStatus.ATIVA;
        this.quantidade = dados.quantidade();
    }
}

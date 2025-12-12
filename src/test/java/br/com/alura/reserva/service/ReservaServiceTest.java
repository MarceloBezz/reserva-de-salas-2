package br.com.alura.reserva.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alura.reserva.infra.exception.RegraDeNegocioException;
import br.com.alura.reserva.model.Reserva.DadosReserva;
import br.com.alura.reserva.model.Reserva.Reserva;
import br.com.alura.reserva.model.Reserva.ReservaDTO;
import br.com.alura.reserva.model.Reserva.ReservaStatus;
import br.com.alura.reserva.model.Sala.Sala;
import br.com.alura.reserva.model.Usuario.Usuario;
import br.com.alura.reserva.repository.ReservaRepository;
import br.com.alura.reserva.repository.SalaRepository;

@ExtendWith(MockitoExtension.class)
public class ReservaServiceTest {

    @InjectMocks
    private ReservaService service;

    @Mock
    private ReservaRepository repository;

    @Mock
    private SalaRepository salaRepository;

    @Mock
    private Sala sala;

    @Mock
    private Usuario usuario;

    @Captor
    private ArgumentCaptor<Reserva> captor;

    @Mock
    private Reserva reservaFutura;

    @Mock
    private Reserva reservaPassada;

    private ReservaDTO dto;

    @Test
    void deveCriarReserva() {
        // ASSEGURE
        dto = new ReservaDTO(LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(3), 5, 1L);
        BDDMockito.given(salaRepository.findById(dto.salaId())).willReturn(Optional.of(sala));

        // ACT
        service.agendarReserva(dto, usuario);

        // ASSERT
        BDDMockito.then(repository).should().save(captor.capture());
        Reserva reservaBD = captor.getValue();

        assertEquals(reservaBD.getInicio(), dto.inicio());
        assertEquals(reservaBD.getFim(), dto.fim());
        assertEquals(reservaBD.getSala(), sala);
        assertEquals(reservaBD.getUsuario().getId(), usuario.getId());
    }

    @Test
    void deveriaRetornarReservasPassadasEFuturas() {
        // ASSEGURE
        List<Reserva> futuras = List.of(reservaFutura);
        List<Reserva> passadas = List.of(reservaPassada);

        BDDMockito.given(
                repository.findByUsuarioAndInicioAfter(BDDMockito.eq(usuario), BDDMockito.any(LocalDateTime.class)))
                .willReturn(futuras);
        BDDMockito.given(
                repository.findByUsuarioAndInicioBefore(BDDMockito.eq(usuario), BDDMockito.any(LocalDateTime.class)))
                .willReturn(passadas);
        BDDMockito.given(reservaFutura.getSala()).willReturn(sala);
        BDDMockito.given(reservaPassada.getSala()).willReturn(sala);

        // ACT
        Map<String, List<DadosReserva>> resultado = service.buscarTodasReservas(usuario);

        // ASSERT
        assertEquals(2, resultado.size());
        assertTrue(resultado.containsKey("ReservasPassadas"));
        assertTrue(resultado.containsKey("ProximasReservas"));
    }

    @Test
    void naoDeveriaCancelarUmaReservaJaCancelada() {
        // ASSEGURE
        Reserva reservaCancelar = new Reserva(1L, usuario, sala, LocalDateTime.now(), LocalDateTime.now().plusHours(2),
                ReservaStatus.CANCELADA, 2);
        BDDMockito.given(repository.findByIdComUsuario(anyLong())).willReturn(reservaCancelar);

        // ACT + ASSERT
        assertThrows(RegraDeNegocioException.class,() -> service.cancelarReserva(reservaCancelar.getId(), usuario));
    }

    @Test
    void deveriaCancelarReserva() {
        // ASSEGURE
        Reserva reservaCancelar = new Reserva(1L, usuario, sala, LocalDateTime.now(), LocalDateTime.now().plusHours(2),
                ReservaStatus.ATIVA, 2);
        BDDMockito.given(repository.findByIdComUsuario(anyLong())).willReturn(reservaCancelar);

        // ACT
        service.cancelarReserva(anyLong(), usuario);

        // ASSERT
        assertEquals(reservaCancelar.getStatus(), ReservaStatus.CANCELADA);
    }
}

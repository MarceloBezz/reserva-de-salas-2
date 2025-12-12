package br.com.alura.reserva.validacoes;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alura.reserva.infra.exception.RegraDeNegocioException;
import br.com.alura.reserva.model.Reserva.ReservaDTO;
import br.com.alura.reserva.model.Sala.Sala;
import br.com.alura.reserva.repository.ReservaRepository;

@ExtendWith(MockitoExtension.class)
public class ValidadoresTest {
    @InjectMocks
    private ValidarCapacidadeSala validadorCapacidadeSala;

    @InjectMocks
    private ValidarSalaAtiva validadorSalaAtiva;

    @InjectMocks
    private ValidarSalaJaAgendada validadorSalaJaAgendada;

    @Mock
    private Sala sala;

    @Mock
    private ReservaDTO dto;

    @Mock
    private ReservaRepository repository;

    @Test
    void naoDeveCriarReservaAcimaDoLimiteMaximoDePessoasDaSala() {
        BDDMockito.given(sala.getCapacidade()).willReturn(39);
        BDDMockito.given(dto.quantidade()).willReturn(40);

        assertThrows(RegraDeNegocioException.class, () -> validadorCapacidadeSala.validar(sala, dto));
    }

    @Test
    void deveriaPermitirCriarReservaAbaixoDoLimiteDePessoasDaSala() {
        BDDMockito.given(sala.getCapacidade()).willReturn(40);
        BDDMockito.given(dto.quantidade()).willReturn(39);

        assertDoesNotThrow(() -> validadorCapacidadeSala.validar(sala, dto));
    }

    @Test
    void naoDeveriaReservarSalaInativa() {
        BDDMockito.given(sala.isAtiva()).willReturn(false);

        assertThrows(RegraDeNegocioException.class, () -> validadorSalaAtiva.validar(sala, dto));
    }

    @Test
    void deveriaReservarSalaAtiva() {
        BDDMockito.given(sala.isAtiva()).willReturn(true);

        assertDoesNotThrow(() -> validadorSalaAtiva.validar(sala, dto));
    }

    @Test
    void naoDeveriaReservarSalaJaAgendada() {
        BDDMockito.given(dto.fim()).willReturn(LocalDateTime.now().plusHours(5));
        BDDMockito.given(dto.inicio()).willReturn(LocalDateTime.now().plusHours(3));
        BDDMockito.given(repository.existsBySalaAndInicioLessThanAndFimGreaterThanAndStatus(any(), any(), any(), any()))
                .willReturn(true);

        assertThrows(RegraDeNegocioException.class, () -> validadorSalaJaAgendada.validar(sala, dto));
    }

    @Test
    void deveriaReservarSalaNaoAgendada() {
        BDDMockito.given(dto.fim()).willReturn(LocalDateTime.now().plusHours(5));
        BDDMockito.given(dto.inicio()).willReturn(LocalDateTime.now().plusHours(3));
        BDDMockito.given(repository.existsBySalaAndInicioLessThanAndFimGreaterThanAndStatus(any(), any(), any(), any()))
                .willReturn(false);

        assertDoesNotThrow(() -> validadorSalaJaAgendada.validar(sala, dto));
    }
}

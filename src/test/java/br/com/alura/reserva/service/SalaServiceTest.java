package br.com.alura.reserva.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alura.reserva.model.Sala.Sala;
import br.com.alura.reserva.model.Sala.SalaDTO;
import br.com.alura.reserva.repository.SalaRepository;

@ExtendWith(MockitoExtension.class)
public class SalaServiceTest {

    @InjectMocks
    private SalaService service;

    @Mock
    private SalaRepository repository;

    @Captor
    private ArgumentCaptor<Sala> captor;

    private SalaDTO dto;
    
    @Test
    void deveriaCriarSala() {
        // ASSEGURE
        dto = new SalaDTO("Sala teste","Teste", 10);

        //ACT
        service.criarSala(dto);

        //ASSERT
        BDDMockito.then(repository).should().save(captor.capture());
        Sala salaBD = captor.getValue();

        assertEquals(salaBD.getNome(), dto.nome());
        assertEquals(salaBD.getDescricao(), dto.descricao());
        assertEquals(salaBD.getCapacidade(), dto.capacidade());
        assertEquals(salaBD.isAtiva(), true);
    }
}

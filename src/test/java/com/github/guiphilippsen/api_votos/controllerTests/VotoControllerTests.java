package com.github.guiphilippsen.api_votos.controllerTests;

import com.github.guiphilippsen.api_votos.controller.VotoController;
import com.github.guiphilippsen.api_votos.entity.Apuracao;
import com.github.guiphilippsen.api_votos.entity.Voto;
import com.github.guiphilippsen.api_votos.service.VotoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class VotoControllerTests {
    @Mock
    private VotoService votoService;

    @InjectMocks
    private VotoController votoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvarComSucesso() {
        Voto voto = new Voto();
        Long eleitorId = 1L;
        String comprovante = "comprovante123";

        when(votoService.votar(voto, eleitorId)).thenReturn(comprovante);

        ResponseEntity<String> response = votoController.salvar(voto, eleitorId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(comprovante, response.getBody());
        verify(votoService, times(1)).votar(voto, eleitorId);
    }

    @Test
    void testSalvarComErro() {
        Voto voto = new Voto();
        Long eleitorId = 1L;

        when(votoService.votar(voto, eleitorId)).thenThrow(new RuntimeException("Erro ao votar"));

        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            votoController.salvar(voto, eleitorId);
        });

        assertEquals("Erro ao votar", exception.getMessage());
        verify(votoService, times(1)).votar(voto, eleitorId);
    }

    @Test
    void testApuracaoComSucesso() {
        Apuracao apuracao = new Apuracao();

        when(votoService.apurarVotos()).thenReturn(apuracao);

        ResponseEntity<?> response = votoController.apuracao();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(apuracao, response.getBody());
        verify(votoService, times(1)).apurarVotos();
    }

    @Test
    void testApuracaoComErro() {
        when(votoService.apurarVotos()).thenThrow(new RuntimeException("Erro ao apurar votos"));

        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            votoController.apuracao();
        });

        assertEquals("Erro ao apurar votos", exception.getMessage());
        verify(votoService, times(1)).apurarVotos();
    }
}

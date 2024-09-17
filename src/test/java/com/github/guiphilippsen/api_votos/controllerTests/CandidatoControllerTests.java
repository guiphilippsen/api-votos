package com.github.guiphilippsen.api_votos.controllerTests;

import com.github.guiphilippsen.api_votos.controller.CandidatoController;
import com.github.guiphilippsen.api_votos.entity.Candidato;
import com.github.guiphilippsen.api_votos.service.CandidatoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CandidatoControllerTests {
    @Mock
    private CandidatoService candidatoService;

    @InjectMocks
    private CandidatoController candidatoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCandidatoComSucesso() {
        Candidato candidato = new Candidato();
        String mensagemEsperada = "Candidato salvo com sucesso";

        when(candidatoService.save(candidato)).thenReturn(mensagemEsperada);

        ResponseEntity<String> response = candidatoController.save(candidato);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mensagemEsperada, response.getBody());
        verify(candidatoService, times(1)).save(candidato);
    }

    @Test
    void testSaveCandidatoComErro() {
        Candidato candidato = new Candidato();

        when(candidatoService.save(candidato)).thenThrow(new RuntimeException("Erro ao salvar candidato"));

        ResponseEntity<String> response = candidatoController.save(candidato);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: Erro ao salvar candidato", response.getBody());
        verify(candidatoService, times(1)).save(candidato);
    }

    @Test
    void testUpdateCandidatoComSucesso() {
        Candidato candidato = new Candidato();
        long id = 1L;
        String mensagemEsperada = "Candidato atualizado com sucesso";

        when(candidatoService.update(candidato, id)).thenReturn(mensagemEsperada);

        ResponseEntity<String> response = candidatoController.update(candidato, id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mensagemEsperada, response.getBody());
        verify(candidatoService, times(1)).update(candidato, id);
    }

    @Test
    void testUpdateCandidatoComErro() {
        Candidato candidato = new Candidato();
        long id = 1L;

        when(candidatoService.update(candidato, id)).thenThrow(new RuntimeException("Erro ao atualizar candidato"));

        ResponseEntity<String> response = candidatoController.update(candidato, id);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: Erro ao atualizar candidato", response.getBody());
        verify(candidatoService, times(1)).update(candidato, id);
    }

    @Test
    void testFindAllVereadorComSucesso() {
        List<Candidato> listaCandidatos = new ArrayList<>();
        listaCandidatos.add(new Candidato());
        listaCandidatos.add(new Candidato());

        when(candidatoService.findAllVereador()).thenReturn(listaCandidatos);

        ResponseEntity<List<Candidato>> response = candidatoController.findAllVereador();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listaCandidatos, response.getBody());
        verify(candidatoService, times(1)).findAllVereador();
    }

    @Test
    void testFindAllVereadorComErro() {
        when(candidatoService.findAllVereador()).thenThrow(new RuntimeException("Erro ao buscar candidatos"));

        ResponseEntity<List<Candidato>> response = candidatoController.findAllVereador();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
        verify(candidatoService, times(1)).findAllVereador();
    }

    @Test
    void testDeleteCandidatoComSucesso() {
        Candidato candidato = new Candidato();
        long id = 1L;
        String mensagemEsperada = "Candidato removido com sucesso";

        when(candidatoService.delete(candidato, id)).thenReturn(mensagemEsperada);

        ResponseEntity<String> response = candidatoController.delete(candidato, id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mensagemEsperada, response.getBody());
        verify(candidatoService, times(1)).delete(candidato, id);
    }

    @Test
    void testDeleteCandidatoComErro() {
        Candidato candidato = new Candidato();
        long id = 1L;

        when(candidatoService.delete(candidato, id)).thenThrow(new RuntimeException("Erro ao remover candidato"));

        ResponseEntity<String> response = candidatoController.delete(candidato, id);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
        verify(candidatoService, times(1)).delete(candidato, id);
    }
}

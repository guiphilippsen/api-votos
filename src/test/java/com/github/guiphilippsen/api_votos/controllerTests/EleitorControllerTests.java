package com.github.guiphilippsen.api_votos.controllerTests;

import com.github.guiphilippsen.api_votos.controller.EleitorController;
import com.github.guiphilippsen.api_votos.entity.Eleitor;
import com.github.guiphilippsen.api_votos.service.EleitorService;
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

public class EleitorControllerTests {
    @Mock
    private EleitorService eleitorService;

    @InjectMocks
    private EleitorController eleitorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveEleitorComSucesso() {
        Eleitor eleitor = new Eleitor();
        String mensagemEsperada = "Eleitor cadastrado!";

        when(eleitorService.saveEleitor(eleitor)).thenReturn(mensagemEsperada);

        ResponseEntity<String> response = eleitorController.save(eleitor);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mensagemEsperada, response.getBody());
        verify(eleitorService, times(1)).saveEleitor(eleitor);
    }

    @Test
    void testSaveEleitorComErro() {
        Eleitor eleitor = new Eleitor();
        when(eleitorService.saveEleitor(eleitor)).thenThrow(new RuntimeException("Erro ao salvar eleitor"));

        ResponseEntity<String> response = eleitorController.save(eleitor);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: Erro ao salvar eleitor", response.getBody());
        verify(eleitorService, times(1)).saveEleitor(eleitor);
    }

    @Test
    void testUpdateEleitorComSucesso() {
        Eleitor eleitor = new Eleitor();
        long id = 1L;
        String mensagemEsperada = "Eleitor atualizado!";

        when(eleitorService.updateEleitorifDontHaveCpfAndEmail(eleitor, id)).thenReturn(mensagemEsperada);

        ResponseEntity<String> response = eleitorController.update(eleitor, id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mensagemEsperada, response.getBody());
        verify(eleitorService, times(1)).updateEleitorifDontHaveCpfAndEmail(eleitor, id);
    }

    @Test
    void testUpdateEleitorComErro() {
        Eleitor eleitor = new Eleitor();
        long id = 1L;

        when(eleitorService.updateEleitorifDontHaveCpfAndEmail(eleitor, id)).thenThrow(new RuntimeException("Erro ao atualizar eleitor"));

        ResponseEntity<String> response = eleitorController.update(eleitor, id);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: Erro ao atualizar eleitor", response.getBody());
        verify(eleitorService, times(1)).updateEleitorifDontHaveCpfAndEmail(eleitor, id);
    }

    @Test
    void testFindAllComSucesso() {
        List<Eleitor> listaEleitores = new ArrayList<>();
        listaEleitores.add(new Eleitor());
        listaEleitores.add(new Eleitor());

        when(eleitorService.findAll()).thenReturn(listaEleitores);

        ResponseEntity<List<Eleitor>> response = eleitorController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listaEleitores, response.getBody());
        verify(eleitorService, times(1)).findAll();
    }

    @Test
    void testFindAllComErro() {
        when(eleitorService.findAll()).thenThrow(new RuntimeException("Erro ao buscar eleitores"));

        ResponseEntity<List<Eleitor>> response = eleitorController.findAll();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
        verify(eleitorService, times(1)).findAll();
    }

    @Test
    void testDeleteEleitorComSucesso() {
        Eleitor eleitor = new Eleitor();
        long id = 1L;
        String mensagemEsperada = "Eleitor removido";

        when(eleitorService.deleteEleitor(eleitor, id)).thenReturn(mensagemEsperada);

        ResponseEntity<String> response = eleitorController.delete(eleitor, id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mensagemEsperada, response.getBody());
        verify(eleitorService, times(1)).deleteEleitor(eleitor, id);
    }

    @Test
    void testDeleteEleitorComErro() {
        Eleitor eleitor = new Eleitor();
        long id = 1L;

        when(eleitorService.deleteEleitor(eleitor, id)).thenThrow(new RuntimeException("Erro ao remover eleitor"));

        ResponseEntity<String> response = eleitorController.delete(eleitor, id);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro ao remover eleitor", response.getBody());
        verify(eleitorService, times(1)).deleteEleitor(eleitor, id);
    }
}

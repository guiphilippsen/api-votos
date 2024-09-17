package com.github.guiphilippsen.api_votos.serviceTests;

import com.github.guiphilippsen.api_votos.entity.Candidato;
import com.github.guiphilippsen.api_votos.entity.enums.Funcao;
import com.github.guiphilippsen.api_votos.entity.enums.StatusCandidato;
import com.github.guiphilippsen.api_votos.repository.CandidatoRepository;
import com.github.guiphilippsen.api_votos.service.CandidatoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CandidatoServiceTests {
    @Mock
    private CandidatoRepository candidatoRepository;

    @InjectMocks
    private CandidatoService candidatoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        Candidato candidato = new Candidato();
        candidato.setNome("João");
        candidato.setCpf("123456789");

        when(candidatoRepository.save(any(Candidato.class))).thenReturn(candidato);

        String result = candidatoService.save(candidato);

        assertEquals("Candidato cadastrado no banco de dados!", result);
        assertEquals(StatusCandidato.ATIVO, candidato.getStatus());
        verify(candidatoRepository, times(1)).save(candidato);
    }

    @Test
    void testDeleteCandidatoExistente() {
        Candidato candidato = new Candidato();
        candidato.setStatus(StatusCandidato.ATIVO);

        when(candidatoRepository.findById(anyLong())).thenReturn(Optional.of(candidato));

        String result = candidatoService.delete(candidato, 1L);

        assertEquals("Candidato removido", result);
        assertEquals(StatusCandidato.INATIVO, candidato.getStatus());
        verify(candidatoRepository, times(1)).save(candidato);
    }

    @Test
    void testDeleteCandidatoJaExcluido() {
        Candidato candidato = new Candidato();
        candidato.setStatus(StatusCandidato.INATIVO);

        when(candidatoRepository.findById(anyLong())).thenReturn(Optional.of(candidato));

        String result = candidatoService.delete(candidato, 1L);

        assertEquals("Canditado ja foi excluido do banco de dados", result);
        verify(candidatoRepository, times(0)).save(candidato);
    }

    @Test
    void testDeleteCandidatoNaoEncontrado() {
        when(candidatoRepository.findById(anyLong())).thenReturn(Optional.empty());

        String result = candidatoService.delete(new Candidato(), 1L);

        assertEquals("Candidato não encontrado no banco de dados", result);
        verify(candidatoRepository, times(0)).save(any(Candidato.class));
    }

    @Test
    void testUpdateCandidatoExistente() {
        Candidato candidato = new Candidato();
        candidato.setNome("João");
        candidato.setCpf("123456789");
        candidato.setFuncao(Funcao.VEREADOR);
        candidato.setNumeroCandidato(101L);

        when(candidatoRepository.findById(anyLong())).thenReturn(Optional.of(candidato));

        String result = candidatoService.update(candidato, 1L);

        assertEquals("Candidato Atulizado", result);
        verify(candidatoRepository, times(1)).save(candidato);
    }

    @Test
    void testFindAllPrefeito() {
        Candidato candidato1 = new Candidato();
        candidato1.setFuncao(Funcao.PREFEITO);
        candidato1.setStatus(StatusCandidato.ATIVO);

        when(candidatoRepository.findByCargoAndStatus(Funcao.PREFEITO, StatusCandidato.ATIVO))
                .thenReturn(Arrays.asList(candidato1));

        List<Candidato> candidatos = candidatoService.findAllPrefeito();

        assertEquals(1, candidatos.size());
        assertEquals(Funcao.PREFEITO, candidatos.get(0).getFuncao());
    }

    @Test
    void testFindAllVereador() {
        Candidato candidato1 = new Candidato();
        candidato1.setFuncao(Funcao.VEREADOR);
        candidato1.setStatus(StatusCandidato.ATIVO);

        when(candidatoRepository.findByCargoAndStatus(Funcao.VEREADOR, StatusCandidato.ATIVO))
                .thenReturn(Arrays.asList(candidato1));

        List<Candidato> candidatos = candidatoService.findAllVereador();

        assertEquals(1, candidatos.size());
        assertEquals(Funcao.VEREADOR, candidatos.get(0).getFuncao());
    }
}

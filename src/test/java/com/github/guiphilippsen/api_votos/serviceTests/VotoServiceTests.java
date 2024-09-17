package com.github.guiphilippsen.api_votos.serviceTests;

import com.github.guiphilippsen.api_votos.entity.Apuracao;
import com.github.guiphilippsen.api_votos.entity.Candidato;
import com.github.guiphilippsen.api_votos.entity.Eleitor;
import com.github.guiphilippsen.api_votos.entity.Voto;
import com.github.guiphilippsen.api_votos.entity.enums.Funcao;
import com.github.guiphilippsen.api_votos.entity.enums.StatusCandidato;
import com.github.guiphilippsen.api_votos.entity.enums.StatusEleitor;
import com.github.guiphilippsen.api_votos.repository.CandidatoRepository;
import com.github.guiphilippsen.api_votos.repository.EleitorRepository;
import com.github.guiphilippsen.api_votos.repository.VotoRepository;
import com.github.guiphilippsen.api_votos.service.VotoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class VotoServiceTests {
    @Mock
    private VotoRepository votoRepository;

    @Mock
    private EleitorRepository eleitorRepository;

    @Mock
    private CandidatoRepository candidatoRepository;

    @InjectMocks
    private VotoService votoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testVotarComSucesso() {
        Eleitor eleitor = new Eleitor();
        eleitor.setStatus(StatusEleitor.APTO);

        Candidato prefeito = new Candidato();
        prefeito.setId(1L);
        prefeito.setFuncao(Funcao.PREFEITO);
        prefeito.setStatus(StatusCandidato.ATIVO);

        Candidato vereador = new Candidato();
        vereador.setId(2L);
        vereador.setFuncao(Funcao.VEREADOR);
        vereador.setStatus(StatusCandidato.ATIVO);

        Voto voto = new Voto();
        voto.setCandidatoPrefeito(prefeito);
        voto.setCandidatoVereador(vereador);

        when(eleitorRepository.findById(anyLong())).thenReturn(Optional.of(eleitor));
        when(candidatoRepository.findById(prefeito.getId())).thenReturn(Optional.of(prefeito));
        when(candidatoRepository.findById(vereador.getId())).thenReturn(Optional.of(vereador));

        String comprovante = votoService.votar(voto, 1L);

        assertEquals(36, comprovante.length());  // UUID tem 36 caracteres
        verify(votoRepository, times(1)).save(voto);
    }

    @Test
    void testVotarEleitorBloqueado() {
        Eleitor eleitor = new Eleitor();
        eleitor.setStatus(StatusEleitor.BLOQUEADO);

        when(eleitorRepository.findById(anyLong())).thenReturn(Optional.of(eleitor));

        Voto voto = new Voto();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            votoService.votar(voto, 1L);
        });

        assertEquals("Esse eleitor esta bloqueado!", exception.getMessage());
    }

    @Test
    void testValidarCandidatoInativo() {
        Candidato candidato = new Candidato();
        candidato.setStatus(StatusCandidato.INATIVO);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            votoService.ValidarCandidato(candidato);
        });

        assertEquals("Candidato invalido/inativo", exception.getMessage());
    }

    @Test
    void testApurarVotos() {
        Candidato prefeito = new Candidato();
        prefeito.setId(1L);
        prefeito.setFuncao(Funcao.PREFEITO);
        prefeito.setVotos(10);

        Candidato vereador = new Candidato();
        vereador.setId(2L);
        vereador.setFuncao(Funcao.VEREADOR);
        vereador.setVotos(20);

        when(candidatoRepository.findByCargoAndStatus(Funcao.PREFEITO, StatusCandidato.ATIVO))
                .thenReturn(Arrays.asList(prefeito));
        when(candidatoRepository.findByCargoAndStatus(Funcao.VEREADOR, StatusCandidato.ATIVO))
                .thenReturn(Arrays.asList(vereador));
        when(votoRepository.count()).thenReturn(30L);
        when(votoRepository.countByCandidatoPrefeitoId(prefeito.getId())).thenReturn(10);
        when(votoRepository.countByCandidatoVereadorId(vereador.getId())).thenReturn(20);

        Apuracao apuracao = votoService.apurarVotos();

        assertEquals(30, apuracao.getTotalVotos());
        assertEquals(1, apuracao.getCadidatoPrefeito().size());
        assertEquals(1, apuracao.getCandidatoVereador().size());
        assertEquals(10, apuracao.getCadidatoPrefeito().get(0).getVotos());
        assertEquals(20, apuracao.getCandidatoVereador().get(0).getVotos());
    }
}

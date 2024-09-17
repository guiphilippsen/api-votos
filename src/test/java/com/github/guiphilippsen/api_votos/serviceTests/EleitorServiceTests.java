package com.github.guiphilippsen.api_votos.serviceTests;

import com.github.guiphilippsen.api_votos.entity.Eleitor;
import com.github.guiphilippsen.api_votos.entity.enums.StatusEleitor;
import com.github.guiphilippsen.api_votos.repository.EleitorRepository;
import com.github.guiphilippsen.api_votos.service.EleitorService;
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

public class EleitorServiceTests {
    @Mock
    private EleitorRepository eleitorRepository;

    @InjectMocks
    private EleitorService eleitorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveEleitor() {
        Eleitor eleitor = new Eleitor();
        eleitor.setNome("Carlos");

        when(eleitorRepository.save(any(Eleitor.class))).thenReturn(eleitor);

        String result = eleitorService.saveEleitor(eleitor);

        assertEquals("Eleitor cadastrado!", result);
        verify(eleitorRepository, times(1)).save(eleitor);
    }

    @Test
    void testUpdateEleitorIfDontHaveCpfAndEmail() {
        Eleitor eleitor = new Eleitor();
        eleitor.setNome("Carlos");
        eleitor.setCpf("12345678900");
        eleitor.setEmail("carlos@email.com");

        Eleitor eleitorUpdated = new Eleitor();
        eleitorUpdated.setCpf("98765432100");
        eleitorUpdated.setEmail("carlos_updated@email.com");

        when(eleitorRepository.findById(anyLong())).thenReturn(Optional.of(eleitor));

        String result = eleitorService.updateEleitorifDontHaveCpfAndEmail(eleitorUpdated, 1L);

        assertEquals("Eleitor atualizado", result);
        verify(eleitorRepository, times(1)).updateUserEmailAndCpf(eleitor.getId(), eleitorUpdated.getEmail(), eleitorUpdated.getCpf());
        verify(eleitorRepository, times(1)).updateUserStatus(eleitor.getId(), StatusEleitor.APTO);
    }

    @Test
    void testDeleteEleitor() {
        Eleitor eleitor = new Eleitor();
        eleitor.setStatus(StatusEleitor.APTO);

        when(eleitorRepository.findById(anyLong())).thenReturn(Optional.of(eleitor));

        String result = eleitorService.deleteEleitor(eleitor, 1L);

        assertEquals("Eleitor removido", result);
        assertEquals(StatusEleitor.INATIVO, eleitor.getStatus());
        verify(eleitorRepository, times(1)).updateUserStatus(eleitor.getId(), StatusEleitor.INATIVO);
    }

    @Test
    void testDeleteEleitorWithStatusVotou() {
        Eleitor eleitor = new Eleitor();
        eleitor.setStatus(StatusEleitor.VOTOU);

        when(eleitorRepository.findById(anyLong())).thenReturn(Optional.of(eleitor));

        String result = eleitorService.deleteEleitor(eleitor, 1L);

        assertEquals("Eleitor removido", result);
        verify(eleitorRepository, times(0)).updateUserStatus(anyLong(), any());
    }

    @Test
    void testFindAll() {
        Eleitor eleitor1 = new Eleitor();
        eleitor1.setStatus(StatusEleitor.APTO);

        when(eleitorRepository.findByStatus(StatusEleitor.APTO))
                .thenReturn(Arrays.asList(eleitor1));

        List<Eleitor> eleitores = eleitorService.findAll();

        assertEquals(1, eleitores.size());
        assertEquals(StatusEleitor.APTO, eleitores.get(0).getStatus());
    }
}

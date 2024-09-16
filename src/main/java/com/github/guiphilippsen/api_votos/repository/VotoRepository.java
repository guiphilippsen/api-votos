package com.github.guiphilippsen.api_votos.repository;

import com.github.guiphilippsen.api_votos.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotoRepository extends JpaRepository<Voto, Long> {
    int countByCandidatoPrefeitoId(Long candidatoId);
    int countByCandidatoVereadorId(Long candidatoId);
}

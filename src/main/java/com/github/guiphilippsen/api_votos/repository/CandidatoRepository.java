package com.github.guiphilippsen.api_votos.repository;

import com.github.guiphilippsen.api_votos.entity.Candidato;
import com.github.guiphilippsen.api_votos.entity.enums.Funcao;
import com.github.guiphilippsen.api_votos.entity.enums.StatusCandidato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
    List<Candidato> findByCargoAndStatus(Funcao funcao, StatusCandidato statusCandidato);
}

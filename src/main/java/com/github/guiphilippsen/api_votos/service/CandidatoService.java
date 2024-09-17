package com.github.guiphilippsen.api_votos.service;

import com.github.guiphilippsen.api_votos.entity.Candidato;
import com.github.guiphilippsen.api_votos.entity.enums.Funcao;
import com.github.guiphilippsen.api_votos.entity.enums.StatusCandidato;
import com.github.guiphilippsen.api_votos.repository.CandidatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidatoService {

    @Autowired
    private CandidatoRepository candidatoRepository;

    public String save(Candidato candidato) {
        candidato.setStatus(StatusCandidato.ATIVO);
        this.candidatoRepository.save(candidato);
        return "Candidato cadastrado no banco de dados!";
    }

    public String delete(Candidato candidatoDelete, Long id) {
        Optional<Candidato> candidatoOptional =
                this.candidatoRepository.findById(id);

        if (candidatoOptional.isPresent()) {
            Candidato candidato = candidatoOptional.get();
            if (candidato.getStatus() != StatusCandidato.INATIVO) {
                candidato.setStatus(StatusCandidato.INATIVO);
                this.candidatoRepository.save(candidato);
            } else {
                return "Canditado ja foi excluido do banco de dados";
            }
        } else {
            return "Candidato não encontrado no banco de dados";
        }
        return "Candidato removido";
    }

    public String update(Candidato candidatoUpdate, Long id) {
        Optional<Candidato> candidatoOptional = this.candidatoRepository.findById(id);

        if (candidatoOptional.isPresent()) {
            Candidato candidato = candidatoOptional.get();
            candidato.setNome(
                    candidato.getNome()
            );
            candidato.setCpf(
                    candidato.getCpf()
            );
            candidato.setFuncao(
                    candidato.getFuncao()
            );
            candidato.setNumeroCandidato(
                    candidato.getNumeroCandidato()
            );
            candidato.setStatus(
                    StatusCandidato.ATIVO
            );
            this.candidatoRepository.save(candidato);
            return "Candidato Atulizado";
        }
        return "Candidato não encontrado";
    }

    public List<Candidato> findAllPrefeito() {
        return this.candidatoRepository.findByCargoAndStatus(Funcao.PREFEITO, StatusCandidato.ATIVO);
    }
    public List<Candidato> findAllVereador() {
        return this.candidatoRepository.findByCargoAndStatus(Funcao.VEREADOR, StatusCandidato.ATIVO);
    }
}

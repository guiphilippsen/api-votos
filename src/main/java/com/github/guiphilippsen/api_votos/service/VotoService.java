package com.github.guiphilippsen.api_votos.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VotoService {
    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private EleitorRepository eleitorRepository;

    @Autowired
    private CandidatoRepository candidatoRepository;

    public String votar(Voto voto, Long id_eleitor) {
        Optional<Eleitor> eleitorOptional =
                this.eleitorRepository.findById(id_eleitor);

        Eleitor eleitor = eleitorOptional.get();

        ValidarEleitor(eleitor);

        ValidacaoVoto(voto);

        voto.setHashComprovante(UUID.randomUUID().toString());
        voto.setDataHoraVoto(LocalDateTime.now());
        this.votoRepository.save(voto);

        return voto.getHashComprovante().toString();
    }

    public void ValidarEleitor(Eleitor eleitor) {
        if (eleitor.getStatus() == StatusEleitor.PENDENTE) {
            eleitor.setStatus(StatusEleitor.BLOQUEADO);
            throw new IllegalArgumentException("Esse eleitor não pode votar, ele sera bloqueado!");
        } else if (eleitor.getStatus() == StatusEleitor.BLOQUEADO){
            throw new IllegalArgumentException("Esse eleitor esta bloqueado!");
        } else if (eleitor.getStatus() == StatusEleitor.INATIVO) {
            throw new IllegalArgumentException("Esse eleitor esta inativo");
        } else if (eleitor.getStatus() == StatusEleitor.VOTOU) {
            throw new IllegalArgumentException("Esse eleitor já votou");
        } else if (eleitor.getStatus() == StatusEleitor.APTO) {
            eleitor.setStatus(StatusEleitor.VOTOU);
        }
    }

    public void ValidacaoVoto(Voto voto) {
        Candidato prefeito = candidatoRepository.findById(voto.getCandidatoPrefeito().getId()).get();
        Candidato vereador = candidatoRepository.findById(voto.getCandidatoVereador().getId()).get();

        if (prefeito.getCargo() == Funcao.PREFEITO && vereador.getCargo() == Funcao.VEREADOR) {
            ValidarCandidato(prefeito);
            ValidarCandidato(vereador);
        }else {
            throw new IllegalArgumentException("Informaçoes de voto incorretas!");
        }
    }

    public void ValidarCandidato(Candidato candidato) {
        if (candidato.getStatus() == StatusCandidato.INATIVO) {
            throw new IllegalArgumentException("Candidato invalido/inativo");
        }
    }

    public Apuracao apurarVotos() {
        List<Candidato> prefeito = this.candidatoRepository.findByCargoAndStatus(Funcao.PREFEITO, StatusCandidato.ATIVO);
        List<Candidato> vereador = this.candidatoRepository.findByCargoAndStatus(Funcao.VEREADOR, StatusCandidato.ATIVO);

        Apuracao apuracao = new Apuracao();
        apuracao.setCadidatoPrefeito(prefeito);
        apuracao.setCandidatoVereador(vereador);

        apuracao.setTotalVotos((int) votoRepository.count());

        for (Candidato prefeitoCurrent: prefeito) {
            int votos = votoRepository.countByCandidatoPrefeitoId(prefeitoCurrent.getId());
            prefeitoCurrent.setVotos(votos);
        }
        for (Candidato vereadorCurrent: vereador) {
            int votos = votoRepository.countByCandidatoVereadorId(vereadorCurrent.getId());
            vereadorCurrent.setVotos(votos);
        }

        Collections.sort(prefeito, (list1, list2) -> Integer.compare(list2.getVotos(), list1.getVotos()));
        Collections.sort(vereador, (list1, list2) -> Integer.compare(list2.getVotos(), list1.getVotos()));

        return apuracao;
    }

}

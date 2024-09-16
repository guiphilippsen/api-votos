package com.github.guiphilippsen.api_votos.service;

import com.github.guiphilippsen.api_votos.entity.Eleitor;
import com.github.guiphilippsen.api_votos.entity.enums.StatusEleitor;
import com.github.guiphilippsen.api_votos.repository.EleitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EleitorService {
    @Autowired
    private EleitorRepository eleitorRepository;

    public String saveEleitor(Eleitor eleitor) {
        this.eleitorRepository.save(eleitor);
        return "Eleitor cadastrado!";
    }

    public String updateEleitorifDontHaveCpfAndEmail(Eleitor eleitorUpdated, long id) {
        Optional<Eleitor> eleitorOptional =
                this.eleitorRepository.findById(id);

        if(eleitorOptional.isPresent()) {
            Eleitor eleitor = eleitorOptional.get();
            eleitor.setNome(
                    eleitor.getNome()
            );
            eleitor.setCelular(
                    eleitor.getCelular()
            );
            eleitor.setProfissao(
                    eleitor.getProfissao()
            );
            eleitor.setTelefone(
                    eleitor.getTelefone()
            );
            eleitor.setEmail(
                    eleitorUpdated.getEmail()
            );
            eleitor.setCpf(
                    eleitorUpdated.getCpf()
            );
            eleitor.setStatusEleitor(
                    StatusEleitor.APTO
            );

            this.eleitorRepository.updateUserEmailAndCpf(eleitor.getId(), eleitor.getEmail(), eleitor.getCpf());
            this.eleitorRepository.updateUserStatus(eleitor.getId(), eleitor.getStatusEleitor());
        }
        return "Eleitor atualizado";
    }

    public String deleteEleitor(Eleitor eleitorUpdated, long id) {
        Optional<Eleitor> eleitorOptional =
                this.eleitorRepository.findById(id);

        if (eleitorOptional.isPresent()) {
            Eleitor eleitor = eleitorOptional.get();
            if(eleitor.getStatusEleitor() != StatusEleitor.VOTOU) {
                eleitor.setStatusEleitor(StatusEleitor.INATIVO);
                this.eleitorRepository.updateUserStatus(eleitor.getId(), eleitor.getStatusEleitor());
            }
        }
        return "Eleitor removido";
    }

    public List<Eleitor> findAll() {
        return this.eleitorRepository.findByStatus(StatusEleitor.APTO);
    }
}

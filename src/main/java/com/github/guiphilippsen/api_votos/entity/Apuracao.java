package com.github.guiphilippsen.api_votos.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Apuracao {

    private int totalVotos;
    private List<Candidato> cadidatoPrefeito;
    private List<Candidato> candidatoVereador;

}

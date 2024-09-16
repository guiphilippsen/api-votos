package com.github.guiphilippsen.api_votos.entity;

import com.github.guiphilippsen.api_votos.entity.enums.Funcao;
import com.github.guiphilippsen.api_votos.entity.enums.StatusCandidato;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.scheduling.annotation.EnableScheduling;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Candidato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @CPF
    @Column(unique = true)
    private String cpf;

    @NotBlank
    @Column(unique = true)
    private Long numeroCandidato;

    @Enumerated(EnumType.STRING)
    private Funcao funcao;

    @Enumerated(EnumType.STRING)
    private StatusCandidato status;

    @Transient
    private int votos;
}

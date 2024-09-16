package com.github.guiphilippsen.api_votos.entity;

import com.github.guiphilippsen.api_votos.entity.enums.StatusCandidato;
import com.github.guiphilippsen.api_votos.entity.enums.StatusEleitor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Eleitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @CPF
    private String cpf;

    @NotBlank
    private String profissao;

    @Pattern(regexp = "/^\\(\\d{2}\\)\\d{4}\\-\\d{4}$/")
    private String telefone;

    @NotBlank
    @Pattern(regexp = "/^\\(\\d{2}\\)\\d{5}\\-\\d{4}$/")
    private String celular;

    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String email;

    @Enumerated(EnumType.STRING)
    private StatusEleitor status;

}

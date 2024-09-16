package com.github.guiphilippsen.api_votos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataHoraVoto;

    @ManyToOne(optional = false)
    private Candidato candidatoPrefeito;

    @ManyToOne
    private Candidato candidatoVereador;

    @Column(nullable = false, unique = true)
    private String hashComprovante;
}

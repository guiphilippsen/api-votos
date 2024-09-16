package com.github.guiphilippsen.api_votos.controller;

import com.github.guiphilippsen.api_votos.entity.Apuracao;
import com.github.guiphilippsen.api_votos.entity.Voto;
import com.github.guiphilippsen.api_votos.repository.EleitorRepository;
import com.github.guiphilippsen.api_votos.service.VotoService;
import jakarta.validation.Valid;
import org.graalvm.nativeimage.c.struct.CStruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/api/voto")
@Validated
public class VotoController {
    @Autowired
    VotoService votoService;

    @Autowired
    EleitorRepository eleitorRepository;

    @PostMapping("/save/{eleitor_id}")
    public ResponseEntity<String> salvar(@Valid @RequestBody Voto voto, @PathVariable Long eleitor_id){
        try{
            String mensagem = this.votoService.votar(voto, eleitor_id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/apuracao")
    public ResponseEntity<?> apuracao() {
        try {
            Apuracao apuracao = this.votoService.apurarVotos();
            return new ResponseEntity<>(apuracao, HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

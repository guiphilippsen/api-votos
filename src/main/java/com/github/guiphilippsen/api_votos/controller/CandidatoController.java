package com.github.guiphilippsen.api_votos.controller;

import com.github.guiphilippsen.api_votos.entity.Candidato;
import com.github.guiphilippsen.api_votos.service.CandidatoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/candidato")
public class CandidatoController {

    @Autowired
    private CandidatoService candidatoService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody @Valid Candidato candidato) {
        try {
            String result = this.candidatoService.save(candidato);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "Erro: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody Candidato candidato, @PathVariable long id) {
        try {
            String result = this.candidatoService.update(candidato,id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAllVereador")
    public ResponseEntity<List<Candidato>> findAllVereador(){
        try {
            List<Candidato> lista = this.candidatoService.findAllVereador();
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST );
        }
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<String> delete( Candidato candidato, @PathVariable long id){
        try {
            String mensagem = this.candidatoService.delete(candidato, id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST );
        }
    }
}

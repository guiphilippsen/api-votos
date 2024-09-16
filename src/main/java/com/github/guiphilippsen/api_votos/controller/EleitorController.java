package com.github.guiphilippsen.api_votos.controller;

import com.github.guiphilippsen.api_votos.entity.Eleitor;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.guiphilippsen.api_votos.service.EleitorService;

import java.util.List;

@RestController
@RequestMapping("/api/eleitor")
public class EleitorController {

    @Autowired
    private EleitorService eleitorService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody @Valid Eleitor eleitor){
        try {
            String result = this.eleitorService.saveEleitor(eleitor);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "Erro: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody Eleitor eleitor, @PathVariable long id){
        try {
            String result = this.eleitorService.updateEleitorifDontHaveCpfAndEmail(eleitor, id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro: "+e.getMessage(), HttpStatus.BAD_REQUEST );
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Eleitor>> findAll(){
        try {
            List<Eleitor> lista = this.eleitorService.findAll();
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST );
        }
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<String> delete( Eleitor eleitor, @PathVariable long id){
        try {
            String mensagem = this.eleitorService.deleteEleitor(eleitor, id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST );
        }
    }
}

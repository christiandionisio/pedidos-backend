package com.cdionisio.pedidos.controller;

import com.cdionisio.pedidos.model.ubicacion.Departamento;
import com.cdionisio.pedidos.service.interfaces.IDepartamentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/departamentos")
public class DepartamentoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartamentoController.class);

    @Autowired
    private IDepartamentoService departamentoService;

    @GetMapping
    public Mono<ResponseEntity<Flux<Departamento>>> listDepartamentos() {
        return Mono.just(ResponseEntity.ok(departamentoService.list()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Departamento>> getDepartamentoById(@PathVariable String id) {
        return departamentoService.getById(id)
                .flatMap(res -> Mono.just(new ResponseEntity<>(res, HttpStatus.OK)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> insertDepartamento(@Valid @RequestBody Departamento departamento) {
        return departamentoService.insert(departamento)
                .flatMap(res -> Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @PutMapping
    public Mono<ResponseEntity<Void>> updateDepartamento(@Valid @RequestBody Departamento departamento) {
        return departamentoService.getById(departamento.getId())
                .flatMap(res -> departamentoService.update(departamento))
                .map(res -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteDepartamento(@PathVariable String id) {
        return departamentoService.getById(id)
                .flatMap(res -> departamentoService.delete(id)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}

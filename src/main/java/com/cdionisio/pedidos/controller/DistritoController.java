package com.cdionisio.pedidos.controller;

import com.cdionisio.pedidos.model.ubicacion.Distrito;
import com.cdionisio.pedidos.service.interfaces.IDistritoService;
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
@RequestMapping("/distritos")
public class DistritoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistritoController.class);

    @Autowired
    private IDistritoService service;

    @GetMapping
    public Mono<ResponseEntity<Flux<Distrito>>> listDistritos() {
        return Mono.just(ResponseEntity.ok(service.list()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Distrito>> getDistritoById(@PathVariable String id) {
        return service.getById(id)
                .flatMap(res -> Mono.just(new ResponseEntity<>(res, HttpStatus.OK)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/byIdDepartamento/{id}")
    public Mono<ResponseEntity<Flux<Distrito>>> getDistritoByIdDepartamento(@PathVariable String id) {
        return Mono.just(ResponseEntity.ok(service.findByIdProvincia(id)));
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> insertDistrito(@Valid @RequestBody Distrito distrito) {
        return service.insert(distrito)
                .flatMap(res -> Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @PutMapping
    public Mono<ResponseEntity<Void>> updateDistrito(@Valid @RequestBody Distrito distrito) {
        return service.getById(distrito.getId())
                .flatMap(res -> service.update(distrito))
                .map(res -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteDistrito(@PathVariable String id) {
        return service.getById(id)
                .flatMap(res -> service.delete(id)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}

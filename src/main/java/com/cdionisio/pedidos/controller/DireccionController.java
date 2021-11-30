package com.cdionisio.pedidos.controller;

import com.cdionisio.pedidos.model.Direccion;
import com.cdionisio.pedidos.service.interfaces.IDireccionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/direcciones")
@PreAuthorize("hasRole('ADMIN')")
public class DireccionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DireccionController.class);

    @Autowired
    private IDireccionService service;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Mono<ResponseEntity<Flux<Direccion>>> listDireccion() {
        return Mono.just(ResponseEntity.ok(service.list()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Mono<ResponseEntity<Direccion>> getDireccionById(@PathVariable String id) {
        return service.getById(id)
                .flatMap(res -> Mono.just(new ResponseEntity<>(res, HttpStatus.OK)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> insertDireccion(@Valid @RequestBody Direccion direccion) {
        return service.insert(direccion)
                .flatMap(res -> Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @PutMapping
    public Mono<ResponseEntity<Void>> updateDireccion(@Valid @RequestBody Direccion direccion) {
        return service.getById(direccion.getId())
                .flatMap(res -> service.update(direccion))
                .map(res -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteDireccion(@PathVariable String id) {
        return service.getById(id)
                .flatMap(res -> service.delete(id)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}

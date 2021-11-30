package com.cdionisio.pedidos.controller;

import com.cdionisio.pedidos.model.ubicacion.Provincia;
import com.cdionisio.pedidos.service.interfaces.IProvinciaService;
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
@RequestMapping("/provincias")
@PreAuthorize("hasRole('ADMIN')")
public class ProvinciaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProvinciaController.class);

    @Autowired
    private IProvinciaService service;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Mono<ResponseEntity<Flux<Provincia>>> listProvincias() {
        return Mono.just(ResponseEntity.ok(service.list()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Mono<ResponseEntity<Provincia>> getProvinciaById(@PathVariable String id) {
        return service.getById(id)
                .flatMap(res -> Mono.just(new ResponseEntity<>(res, HttpStatus.OK)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/byIdDepartamento/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Mono<ResponseEntity<Flux<Provincia>>> getProvinciaByIdDepartamento(@PathVariable String id) {
        return Mono.just(ResponseEntity.ok(service.findByIdDepartament(id)));
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> insertProvincia(@Valid @RequestBody Provincia provincia) {
        return service.insert(provincia)
                .flatMap(res -> Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @PutMapping
    public Mono<ResponseEntity<Void>> updateProvincia(@Valid @RequestBody Provincia provincia) {
        return service.getById(provincia.getId())
                .flatMap(res -> service.update(provincia))
                .map(res -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteProvincia(@PathVariable String id) {
        return service.getById(id)
                .flatMap(res -> service.delete(id)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

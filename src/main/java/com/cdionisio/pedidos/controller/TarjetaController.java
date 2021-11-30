package com.cdionisio.pedidos.controller;

import com.cdionisio.pedidos.model.Tarjeta;
import com.cdionisio.pedidos.service.interfaces.ITarjetaService;
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
@RequestMapping("/tarjetas")
@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
public class TarjetaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TarjetaController.class);

    @Autowired
    private ITarjetaService tarjetaService;

    @GetMapping
    public Mono<ResponseEntity<Flux<Tarjeta>>> listTarjetas() {
        return Mono.just(ResponseEntity.ok(tarjetaService.list()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Tarjeta>> getTarjetaById(@PathVariable String id) {
        return tarjetaService.getById(id)
                .flatMap(res -> Mono.just(new ResponseEntity<>(res, HttpStatus.OK)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> insertTarjeta(@Valid @RequestBody Tarjeta tarjeta) {
        return tarjetaService.insert(tarjeta)
                .flatMap(res -> Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @PutMapping
    public Mono<ResponseEntity<Void>> updateTarjeta(@Valid @RequestBody Tarjeta tarjeta) {
        return tarjetaService.getById(tarjeta.getId())
                .flatMap(res -> tarjetaService.update(tarjeta))
                .map(res -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteTarjeta(@PathVariable String id) {
        return tarjetaService.getById(id)
                .flatMap(res -> tarjetaService.delete(id)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}

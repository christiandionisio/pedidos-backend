package com.cdionisio.pedidos.controller;

import com.cdionisio.pedidos.model.Factura;
import com.cdionisio.pedidos.service.interfaces.IFacturaService;
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
import java.time.LocalDate;

@RestController
@RequestMapping("/facturas")
@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
public class FacturaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FacturaController.class);

    @Autowired
    private IFacturaService facturaService;

    @GetMapping
    public Mono<ResponseEntity<Flux<Factura>>> listFacturas() {
        return Mono.just(ResponseEntity.ok(facturaService.list()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Factura>> getFacturaById(@PathVariable String id) {
        return facturaService.getById(id)
                .flatMap(res -> Mono.just(new ResponseEntity<>(res, HttpStatus.OK)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> insertProduct(@Valid @RequestBody Factura factura) {
        factura.setFechaEmision(LocalDate.now().toString());
        return facturaService.insert(factura)
                .flatMap(res -> Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @PutMapping
    public Mono<ResponseEntity<Void>> updateProduct(@Valid @RequestBody Factura factura) {
        return facturaService.getById(factura.getId())
                .flatMap(res -> facturaService.update(factura))
                .map(res -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable String id) {
        return facturaService.getById(id)
                .flatMap(res -> facturaService.delete(id)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}

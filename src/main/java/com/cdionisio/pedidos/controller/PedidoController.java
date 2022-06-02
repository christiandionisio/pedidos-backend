package com.cdionisio.pedidos.controller;

import com.cdionisio.pedidos.model.Pedido;
import com.cdionisio.pedidos.service.interfaces.IPedidoService;
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
@RequestMapping("/pedidos")
@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
public class PedidoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PedidoController.class);

    @Autowired
    private IPedidoService pedidoService;

    @GetMapping
    public Mono<ResponseEntity<Flux<Pedido>>> listPedidos() {
        return Mono.just(ResponseEntity.ok(pedidoService.list()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Pedido>> getPedidoById(@PathVariable String id) {
        return pedidoService.getById(id)
                .flatMap(res -> Mono.just(new ResponseEntity<>(res, HttpStatus.OK)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getByIdFactura/{id}")
    public Mono<ResponseEntity<Flux<Pedido>>> getPedidoByIdFactura(@PathVariable String id) {
        return Mono.just(ResponseEntity.ok(pedidoService.buscarPorFacturaId(id)));
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> insertProduct(@Valid @RequestBody Pedido pedido) {
        pedido.setFechaPedido(LocalDate.now().toString());
        return pedidoService.insert(pedido)
                .flatMap(res -> Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @PutMapping
    public Mono<ResponseEntity<Void>> updateProduct(@Valid @RequestBody Pedido pedido) {
        return pedidoService.getById(pedido.getId())
                .flatMap(res -> pedidoService.update(pedido))
                .map(res -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable String id) {
        return pedidoService.getById(id)
                .flatMap(res -> pedidoService.delete(id)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}

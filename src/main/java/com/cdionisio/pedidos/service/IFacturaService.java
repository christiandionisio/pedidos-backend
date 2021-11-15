package com.cdionisio.pedidos.service;

import com.cdionisio.pedidos.model.Factura;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IFacturaService {

    Flux<Factura> list();
    Mono<Factura> getById(String id);
    Mono<Factura> insert(Factura factura);
    Mono<Factura> update(Factura factura);
    Mono<Void> delete(String id);
}

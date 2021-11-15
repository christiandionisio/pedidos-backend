package com.cdionisio.pedidos.service;

import com.cdionisio.pedidos.model.Pedido;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPedidoService {
    Flux<Pedido> list();
    Mono<Pedido> getById(String id);
    Mono<Pedido> insert(Pedido pedido);
    Mono<Pedido> update(Pedido pedido);
    Mono<Void> delete(String id);
}

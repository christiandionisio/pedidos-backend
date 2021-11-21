package com.cdionisio.pedidos.service.interfaces;

import com.cdionisio.pedidos.model.Tarjeta;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITarjetaService {

    Flux<Tarjeta> list();
    Mono<Tarjeta> getById(String id);
    Mono<Tarjeta> insert(Tarjeta tarjeta);
    Mono<Tarjeta> update(Tarjeta tarjeta);
    Mono<Void> delete(String id);

}

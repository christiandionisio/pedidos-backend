package com.cdionisio.pedidos.service.interfaces;

import com.cdionisio.pedidos.model.ubicacion.Distrito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IDistritoService {

    Flux<Distrito> list();
    Mono<Distrito> getById(String id);
    Mono<Distrito> insert(Distrito distrito);
    Mono<Distrito> update(Distrito distrito);
    Mono<Void> delete(String id);
    Flux<Distrito> findByIdProvincia(String idProvincia);
}

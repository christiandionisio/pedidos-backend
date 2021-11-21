package com.cdionisio.pedidos.service.interfaces;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICrudGenericService<T> {

    Flux<T> list();
    Mono<T> getById(String id);
    Mono<T> insert(T t);
    Mono<T> update(T t);
    Mono<Void> delete(String id);



}

package com.cdionisio.pedidos.service.interfaces;

import com.cdionisio.pedidos.model.ubicacion.Departamento;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IDepartamentoService {

    Flux<Departamento> list();
    Mono<Departamento> getById(String id);
    Mono<Departamento> insert(Departamento departamento);
    Mono<Departamento> update(Departamento departamento);
    Mono<Void> delete(String id);
}

package com.cdionisio.pedidos.service.interfaces;

import com.cdionisio.pedidos.model.ubicacion.Provincia;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProvinciaService {

    Flux<Provincia> list();
    Mono<Provincia> getById(String id);
    Mono<Provincia> insert(Provincia provincia);
    Mono<Provincia> update(Provincia provincia);
    Mono<Void> delete(String id);
    Flux<Provincia> findByIdDepartament(String idDepartamento);

}

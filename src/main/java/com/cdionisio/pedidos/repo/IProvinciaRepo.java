package com.cdionisio.pedidos.repo;

import com.cdionisio.pedidos.model.ubicacion.Provincia;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface IProvinciaRepo extends ReactiveMongoRepository<Provincia, String> {
    Flux<Provincia> findByIdDepartamento(String idDerpartamento);
}

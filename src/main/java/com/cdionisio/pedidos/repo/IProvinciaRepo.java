package com.cdionisio.pedidos.repo;

import com.cdionisio.pedidos.model.ubicacion.Provincia;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface IProvinciaRepo extends ReactiveMongoRepository<Provincia, String> {

    @Query("{'idDepartamento' : ?0 }")
    Flux<Provincia> obtenerPorIdDepartamento(String idDerpartamento);
}

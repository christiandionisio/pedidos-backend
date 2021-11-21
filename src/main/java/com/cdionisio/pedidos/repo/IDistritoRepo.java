package com.cdionisio.pedidos.repo;

import com.cdionisio.pedidos.model.ubicacion.Distrito;
import com.cdionisio.pedidos.model.ubicacion.Provincia;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface IDistritoRepo extends ReactiveMongoRepository<Distrito, String> {

    @Query("{'idProvinicia' : ?0 }")
    Flux<Distrito> obtenerPorIdProvinicia(String idProvincia);

}

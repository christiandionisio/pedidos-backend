package com.cdionisio.pedidos.repo;

import com.cdionisio.pedidos.model.ubicacion.Distrito;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Flux;

public interface IDistritoRepo extends IGenericRepo<Distrito> {

    @Query("{'idProvincia' : ?0 }")
    Flux<Distrito> obtenerPorIdProvinicia(String idProvincia);

}

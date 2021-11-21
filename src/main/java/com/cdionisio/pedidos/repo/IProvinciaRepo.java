package com.cdionisio.pedidos.repo;

import com.cdionisio.pedidos.model.ubicacion.Provincia;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Flux;

public interface IProvinciaRepo extends IGenericRepo<Provincia>{

    @Query("{'idDepartamento' : ?0 }")
    Flux<Provincia> obtenerPorIdDepartamento(String idDerpartamento);
}

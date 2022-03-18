package com.cdionisio.pedidos.repo;

import com.cdionisio.pedidos.model.Direccion;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Flux;

public interface IDireccionRepo extends IGenericRepo<Direccion>{
    @Query("{'idCliente' : ?0 }")
    Flux<Direccion> buscarPorIdCliente(String idCliente);
}

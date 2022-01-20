package com.cdionisio.pedidos.repo;

import com.cdionisio.pedidos.model.Producto;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Flux;

public interface IProductoRepo extends IGenericRepo<Producto> {

    @Query("{ 'nombre' : { $regex: ?0 } }")
    Flux<Producto> findByFieldFilters(String nombre);
}

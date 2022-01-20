package com.cdionisio.pedidos.repo;

import com.cdionisio.pedidos.model.Producto;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Flux;

public interface IProductoRepo extends IGenericRepo<Producto> {

    @Query("{ $and: [ { 'nombre' : { $regex: ?0, $options:'i' } }, " +
            "{ 'tipo' : { $regex: ?1, $options:'i' } } ] }")
    Flux<Producto> findByFieldFilters(String nombre, String tipo);
}

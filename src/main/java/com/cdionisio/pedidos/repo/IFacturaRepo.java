package com.cdionisio.pedidos.repo;

import com.cdionisio.pedidos.model.Factura;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Flux;

public interface IFacturaRepo extends IGenericRepo<Factura> {

    @Query("{ $and: [ { 'estado' : { $regex: ?0, $options:'i' } }, " +
            "{ 'fechaEmision' : { $regex: ?1, $options:'i' } } ] }")
    Flux<Factura> findByFieldFilters(String estado, String fecha);
}

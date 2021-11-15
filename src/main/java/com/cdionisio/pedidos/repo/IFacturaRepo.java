package com.cdionisio.pedidos.repo;

import com.cdionisio.pedidos.model.Factura;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IFacturaRepo extends ReactiveMongoRepository<Factura, String> {
}

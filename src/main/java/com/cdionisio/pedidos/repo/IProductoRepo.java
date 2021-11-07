package com.cdionisio.pedidos.repo;

import com.cdionisio.pedidos.model.Producto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IProductoRepo extends ReactiveMongoRepository<Producto, String> {
}

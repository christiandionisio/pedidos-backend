package com.cdionisio.pedidos.repo;

import com.cdionisio.pedidos.model.Pedido;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IPedidoRepo extends ReactiveMongoRepository<Pedido, String> {
}

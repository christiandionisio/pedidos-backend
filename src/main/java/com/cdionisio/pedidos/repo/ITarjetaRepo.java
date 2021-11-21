package com.cdionisio.pedidos.repo;

import com.cdionisio.pedidos.model.Tarjeta;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ITarjetaRepo extends ReactiveMongoRepository<Tarjeta, String> {
}

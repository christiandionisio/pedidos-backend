package com.cdionisio.pedidos.repo;

import com.cdionisio.pedidos.model.ubicacion.Departamento;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IDepartamentoRepo extends ReactiveMongoRepository<Departamento, String> {
}

package com.cdionisio.pedidos.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.cdionisio.pedidos.model.Cliente;

public interface IClienteRepo extends ReactiveMongoRepository<Cliente, String> {

}

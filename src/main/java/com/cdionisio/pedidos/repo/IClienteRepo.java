package com.cdionisio.pedidos.repo;

import org.springframework.data.mongodb.repository.Query;

import com.cdionisio.pedidos.model.Cliente;
import reactor.core.publisher.Mono;

public interface IClienteRepo extends IGenericRepo<Cliente> {

    @Query("{'correo' : ?0 }")
    Mono<Cliente> buscarPorCorreo(String correo);

}
